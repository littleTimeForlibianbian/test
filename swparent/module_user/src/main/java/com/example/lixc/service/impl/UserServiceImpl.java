package com.example.lixc.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.lixc.entity.Code;
import com.example.lixc.entity.User;
import com.example.lixc.entity.UserRole;
import com.example.lixc.enums.UserStatusEnum;
import com.example.lixc.mapper.CodeMapper;
import com.example.lixc.mapper.SysConfigMapper;
import com.example.lixc.mapper.UserMapper;
import com.example.lixc.mapper.UserRoleMapper;
import com.example.lixc.service.IAsyncService;
import com.example.lixc.service.UserService;
import com.example.lixc.util.RedisContents;
import com.example.lixc.util.RedisPoolUtil;
import com.example.lixc.util.ResultJson;
import com.example.lixc.util.ToolsUtil;
import com.example.lixc.vo.back.AdminUserBack;
import com.example.lixc.vo.query.AdminUserQuery;
import com.example.lixc.vo.query.UserQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.Date;
import java.util.List;

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
            log.info("用户注册】输入参数错误", JSON.toJSONString(userQuery));
            return ResultJson.buildError("输入参数错误");
        }
        String isOpen = sysConfigMapper.selectAll().get(0).getInvitationCodeOpen();
        if ("Y".equals(isOpen)) {//校验邀请码
            Code code = new Code();
            code.setCode(userQuery.getInvitationCode());
            Code codeFromDB = codeMapper.selectByPrimaryKey(code);
            if (codeFromDB == null) {
                log.info("根据【{}】查询到的数据为空");
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
        userMapper.insertSelective(changeQueryToUser(userQuery));
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
     * @param user 用户对象
     * @return
     */
    public ResultJson Logon(UserQuery user) {
        if (StringUtils.isEmpty(user.getUserName())) {
            return ResultJson.buildError("用户名为空");
        }
        //使用用户名和密码进行登录
        int count = userMapper.selectByUserName(user);
        if (count > 0) {
            log.info("登录成功");
            RedisPoolUtil.set(RedisContents.USER_TOKEN + user.getUserName(), "login", expireTime);
        } else {
            return ResultJson.buildError("用户名或者密码错误");
        }
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
            //增加记录表
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
