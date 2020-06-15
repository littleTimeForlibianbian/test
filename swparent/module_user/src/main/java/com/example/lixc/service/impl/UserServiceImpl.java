package com.example.lixc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.lixc.constants.RedisTimeConstant;
import com.example.lixc.entity.Code;
import com.example.lixc.entity.LoginRecord;
import com.example.lixc.entity.User;
import com.example.lixc.entity.UserRole;
import com.example.lixc.enums.UserStatusEnum;
import com.example.lixc.mapper.*;
import com.example.lixc.service.IAsyncService;
import com.example.lixc.service.UserService;
import com.example.lixc.util.*;
import com.example.lixc.vo.back.AdminUserBack;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.query.AdminUserQuery;
import com.example.lixc.vo.query.UserQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/1 13:52
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    @Autowired
    private LoginRecordMapper loginRecordMapper;

    @Resource
    private CodeMapper codeMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private SysConfigMapper sysConfigMapper;

    @Resource
    private IAsyncService asyncService;

    @Value("${sw.redis.expireTime}")
    private long expireTime;
    @Value("${sw.mail.subject}")
    private String subject;
    @Value("${sw.mail.content}")
    private String content;

    /**
     * 注册用户
     *
     * @param userQuery
     * @return
     */
    public ResultJson registerUser(UserQuery userQuery) {
        log.info("registerUser>>>输入参数：" + userQuery.toString());
        if (!ToolsUtil.verifyParams(userQuery.checkParams())) {
            log.info("用户注册输入参数错误,参数为：{}", JSON.toJSONString(userQuery));
            return ResultJson.buildError("输入参数错误");
        }
        String isOpen = sysConfigMapper.selectAll().get(0).getInvitationCodeOpen();
        if ("Y".equals(isOpen)) {//校验邀请码
            Code code = new Code();
            code.setCode(userQuery.getInvitationCode());
            Code codeFromDB = codeMapper.selectByPrimaryKey(code);
            if (codeFromDB == null) {
                log.info("根据{}查询到的数据为空", code);
                return ResultJson.buildError("邀请码错误");
            }
            if (codeFromDB.getUsedNum() >= 10) {
                return ResultJson.buildError("邀请码使用次数已满，请联系邀请人重新生成");
            }
            //获取系统设置的 过期时间
            int days = sysConfigMapper.selectAll().get(0).getInvitationCodeExpire();
            if (codeFromDB.getCreateTime().before(DateUtils.addDays(new Date(), -days))) {
                return ResultJson.buildError("邀请码已经过期，请联系邀请人重新生成");
            }
        }
        //校验手机号是否已经注册
        if (userMapper.existsWithPhone(userQuery.getPhone(), "N") > 0) {
            return ResultJson.buildError("手机号已经被注册");
        }
        //校验邮箱是否注册
        if (userMapper.existsWithEmail(userQuery.getEmail(), "N") > 0) {
            return ResultJson.buildError("邮箱已经被注册");
        }
        //添加用户
        User user = changeQueryToUser(userQuery);
        userMapper.insertUseGeneratedKeys(user);
        //添加用户角色表
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(userQuery.getRoleId());
        userRoleMapper.insertSelective(userRole);
        //根据用户的id的base64值发送邮件，增加一个邮件记录表
        String params = Base64.getEncoder().encodeToString(userQuery.getNickName().getBytes());
        //TODO  创建邮件记录表 记录发送邮件具体信息
        asyncService.sendEmailAsync(userQuery.getEmail(), subject, String.format(content, "123", "234"));
        return ResultJson.buildSuccess();
    }

    private User changeQueryToUser(UserQuery userQuery) {
        User user = new User();
        user.setNickName(userQuery.getNickName());
        user.setPassword(userQuery.getPassword());
        user.setStatus(UserStatusEnum.USER_STATUS_REG.getCode());
        user.setEmail(userQuery.getEmail());
        user.setPhone(userQuery.getPhone());
        user.setAdministrator("N");
        user.setCity(userQuery.getCity());
        user.setInvitationCode(userQuery.getInvitationCode());
        user.setPainter("N");
        user.setCreateTime(new Date());
        return user;
    }


    /**
     * 用户登录
     *
     * @param userQuery 用户对象
     * @return
     */
    public ResultJson Logon(UserQuery userQuery, HttpServletRequest request) {
        if (StringUtils.isEmpty(userQuery.getUserName())) {
            return ResultJson.buildError("用户名为空");
        }
        if (StringUtils.isEmpty(userQuery.getPassword())) {
            return ResultJson.buildError("密码为空");
        }
        //使用用户名和密码进行登录
        UserBack user = userMapper.selectByUserName(userQuery);
        if (user != null && user.getId() > 0) {
            log.info("登录成功");
            RedisPoolUtil.set(RedisContents.USER_TOKEN + user.getId(), JSONObject.toJSONString(user), expireTime);
        } else {
            return ResultJson.buildError("用户名或者密码错误");
        }
        //插入登录记录表
        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setCreateTime(new Date());
        loginRecord.setLoginIp(NetWorkUtil.getIpAddr(request));
        loginRecord.setUserId(user.getId());
        loginRecord.setUserName(StringUtils.isEmpty(user.getNickName()) ? user.getEmail() : user.getNickName());
        loginRecordMapper.insertSelective(loginRecord);
        return ResultJson.buildSuccess();
    }

    /**
     * 注册用户激活
     *
     * @param param 用户昵称Base64
     * @return
     */
    @Override
    public ResultJson activeRegister(String param) {
        byte[] decode = Base64.getDecoder().decode(param.getBytes());
        String nickName = new String(decode);
        try {
            User user = new User();
            user.setNickName(nickName);
            User userFromDB = userMapper.select(user).get(0);
            if (userFromDB == null) {
                log.info("根据nickName查询记录为空");
                return ResultJson.buildError("激活信息有误!");
            }
            //设置状态为已经激活  更新数据库
            userFromDB.setStatus(UserStatusEnum.USER_STATUS_ACTIVE.getCode());
            userMapper.updateByPrimaryKeySelective(userFromDB);
        } catch (Exception e) {
            log.error("用户{}激活失败,错误信息：{}", nickName, e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError(e.getMessage());
        }
        return ResultJson.buildSuccess();
    }

    /**
     * 忘记密码
     *
     * @param email 邮箱
     * @return
     */
    public ResultJson forgetPassword(String email) {
        if (StringUtils.isEmpty(email)) {
            return ResultJson.buildError("邮箱参数为空");
        }
        UserQuery userQuery = new UserQuery();
        userQuery.setUserName(email);
        UserBack userBack = userMapper.selectByUserName(userQuery);
        if (userBack == null) {
            return ResultJson.buildError("用户不存在");
        }
        //发送邮件
        //产生一个token
        String token = "";
        String suffix = "?email=" + email + "&reset_password_token=" + token;
        //TODO 补充发送邮件参数或者  从DB获取邮件模板，进行填充
        asyncService.sendEmailAsync(email, "忘记密码", "");
        RedisPoolUtil.set(RedisTimeConstant.EMAIL_CODE_RANDOM + email, token, RedisTimeConstant.CACHE_5_MINUTE);
        return ResultJson.buildSuccess();
    }

    /**
     * 重置密码
     *
     * @param userQuery
     * @return
     */
    @Override
    public ResultJson resetPassword(UserQuery userQuery) {
        if (StringUtils.isEmpty(userQuery.getEmail())) {
            return ResultJson.buildError("邮箱为空");
        }
        String token = RedisPoolUtil.get(RedisTimeConstant.EMAIL_CODE_RANDOM + userQuery.getEmail());
        if (StringUtils.isEmpty(token) || token.equals(userQuery.getResetPasswordToken())) {
            return ResultJson.buildError("该链接已经失效,请尝试重新发送密码重置邮件");
        }
        UserBack userBack = userMapper.selectByUserName(userQuery);
        if (userBack == null) {
            return ResultJson.buildError("用户【" + userQuery.getEmail() + "】不存在");
        }
        User user = new User();
        user.setId(userBack.getId());
        user.setPassword(userQuery.getPassword());
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        return ResultJson.buildSuccess("重置密码成功");
    }


    /***********************后台管理员开始********************************/

    /**
     * 获取所有的管理员 分页
     *
     * @return
     */
    public Page<AdminUserBack> selectAdminUsers(AdminUserQuery adminUserQuery) {
        log.info("param:" + adminUserQuery.toString());
        PageHelper.startPage(adminUserQuery.getPageNo(), adminUserQuery.getPageSize());
        List<AdminUserBack> adminUserBacks = userMapper.selectAllAdminUsers(adminUserQuery);
        return (Page<AdminUserBack>) adminUserBacks;
    }

    @Override
    public ResultJson addAdminUser(AdminUserQuery adminUserQuery) {
        try {
            ResultJson verifyParams = adminUserQuery.checkParams();
            if (!ToolsUtil.verifyParams(verifyParams)) {
                return verifyParams;
            }
            //校验手机号是否已经注册
            if (userMapper.existsWithPhone(adminUserQuery.getPhone(), "Y") > 0) {
                return ResultJson.buildError("手机号已经被注册");
            }
            //校验邮箱是否注册
            if (userMapper.existsWithEmail(adminUserQuery.getEmail(), "Y") > 0) {
                return ResultJson.buildError("邮箱已经被注册");
            }
            User user = new User();
            user.setAdministrator("Y");
            user.setPhone(adminUserQuery.getPhone());
            user.setEmail(adminUserQuery.getEmail());
            user.setPassword(adminUserQuery.getPassword());
            user.setEnable("Y");
            user.setRealName(adminUserQuery.getRealName());
            user.setLocation(adminUserQuery.getLocation());
            user.setPosition(adminUserQuery.getPosition());
            user.setCreateTime(new Date());
            user.setRoleId(adminUserQuery.getRoleId());
            userMapper.insertUseGeneratedKeys(user);
            //添加用户角色关联表
            UserRole userRole = new UserRole();
            userRole.setRoleId(adminUserQuery.getRoleId());
            userRole.setUserId(user.getId());
            userRoleMapper.insertSelective(userRole);
        } catch (Exception e) {
            log.error("addAdminUser exception: {}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError(e.getMessage());
        }
        return ResultJson.buildSuccess();
    }

    /**
     * 查询管理员详情
     *
     * @param adminUserQuery
     * @return
     */
    @Override
    public ResultJson detailAdminUser(AdminUserQuery adminUserQuery) {
        if (adminUserQuery.getId() <= 0) {
            return ResultJson.buildError("传入参数对象id为空");
        }
        User user = userMapper.selectByPrimaryKey(adminUserQuery.getId());
        if (user == null) {
            return ResultJson.buildError("ID为{}的对象不存在", adminUserQuery.getId());
        }
        return ResultJson.buildSuccess(user);
    }

    /**
     * 更新管理员
     *
     * @param adminUserQuery
     * @return
     */
    @Override
    public ResultJson updateAdminUser(AdminUserQuery adminUserQuery) {
        try {
            if (adminUserQuery.getId() <= 0) {
                return ResultJson.buildError("传入参数对象id为空");
            }
            User user = userMapper.selectByPrimaryKey(adminUserQuery.getId());
            if (user == null) {
                return ResultJson.buildError("ID为{}的对象不存在", adminUserQuery.getId());
            }
            user.setPhone(adminUserQuery.getPhone());
            user.setPosition(adminUserQuery.getPosition());
            user.setLocation(adminUserQuery.getLocation());
            user.setRoleId(adminUserQuery.getRoleId());
            userMapper.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            log.error("updateAdminUser exception: {}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError(e.getMessage());
        }
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson delAdminUser(AdminUserQuery adminUserQuery) {
        try {
            if (adminUserQuery.getId() <= 0) {
                return ResultJson.buildError("传入参数对象id为空");
            }
            userMapper.deleteByPrimaryKey(adminUserQuery.getId());
        } catch (Exception e) {
            log.error("delAdminUser exception: {}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError(e.getMessage());
        }
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson delAdminUserBatch(String ids) {
        try {
            userMapper.delByIds(ids.split(","));
        } catch (Exception e) {
            log.error("delAdminUser exception: {}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError(e.getMessage());
        }
        return ResultJson.buildSuccess();
    }

    /***********************后台管理员结束********************************/
}
