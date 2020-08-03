package com.example.lixc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.lixc.config.InitConfig;
import com.example.lixc.config.security.utils.SysConfigUtil;
import com.example.lixc.constants.RedisTimeConstant;
import com.example.lixc.entity.*;
import com.example.lixc.enums.UserStatusEnum;
import com.example.lixc.mapper.*;
import com.example.lixc.service.FtpService;
import com.example.lixc.service.IAsyncService;
import com.example.lixc.service.UserPortalService;
import com.example.lixc.util.*;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.query.UserQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/1 13:52
 */
@Service
@Slf4j
public class UserPortalServiceImpl implements UserPortalService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private FtpService ftpService;

    @Autowired
    private UserAttrMapper userAttrMapper;

    @Autowired
    private LoginRecordMapper loginRecordMapper;

    @Resource
    private CodeMapper codeMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Autowired
    private SysImageMapper imageMapper;

    @Resource
    private SysConfigMapper sysConfigMapper;

    @Resource
    private IAsyncService asyncService;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private SysUserTagMapper userTagMapper;

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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultJson registerUser(UserQuery userQuery) {
        log.info("registerUser>>>输入参数：" + userQuery.toString());
        ResultJson verifyParams = userQuery.checkParams();
        if (!ToolsUtil.verifyParams(verifyParams)) {
            log.info("用户注册输入参数错误,参数为：{}", JSON.toJSONString(userQuery));
            return verifyParams;
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
        //校验昵称是否注册
        if (userMapper.existsWithNickName(userQuery.getNickName(), "N") > 0) {
            return ResultJson.buildError("昵称已经被注册");
        }
        //添加用户
        User user = changeQueryToUser(userQuery);
        user.setEnable("Y");
        userMapper.insertUseGeneratedKeys(user);
        //添加用户角色表
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(userQuery.getRoleId() == null ? 1 : userQuery.getRoleId());
        userRoleMapper.insertSelective(userRole);
        //添加用户附加属性
        UserAttr userAttr = new UserAttr();
        userAttr.setUserId(user.getId());
        userAttr.setHeadImage(SysConfigUtil.selectDefaultImageUrl());
        userAttr.setCreateTime(new Date());
        userAttrMapper.insertSelective(userAttr);
        //更新邀请码使用次数
        int i = codeMapper.selectCountByCode(userQuery.getInvitationCode());
        Code code = new Code();
        code.setCode(userQuery.getInvitationCode());
        code.setUsedNum(++i);
        codeMapper.updateCount(code);
        //根据用户的id的base64值发送邮件，增加一个邮件记录表
//        String params = Base64.getEncoder().encodeToString(userQuery.getNickName().getBytes());
//        String content = "http://localhost:8080/public/user/activeRegister?param=" + params;
        //TODO  填充登录页面的路径
        String content = "";
        Map<String, String> map = new HashMap<>();
        map.put("content", content);
        map.put("content1", content);
        asyncService.sendHtmlEmailAsync(userQuery.getEmail(), subject, ToolsUtil.replaceTemplate(this.content, map));
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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultJson logon(UserQuery userQuery, HttpServletRequest request) {
        if (StringUtils.isEmpty(userQuery.getUserName())) {
            return ResultJson.buildError("用户名为空");
        }
        if (StringUtils.isEmpty(userQuery.getPassword())) {
            return ResultJson.buildError("密码为空");
        }
        //使用用户名和密码进行登录
        User user = userMapper.selectBaseByUserName(userQuery);
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
        UserAttr userAttr = userAttrMapper.selectByUserId(user.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("userAttr", userAttr);
        map.put("isPainter", "Y".equalsIgnoreCase(user.getPainter()));
        //缓存redis
        RedisPoolUtil.set(RedisContents.USER_TOKEN + user.getId(), map, expireTime);
        return ResultJson.buildSuccess(map);
    }

    /**
     * 注册用户激活
     *
     * @param param 用户昵称Base64
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
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
        if (!ToolsUtil.regexEmail(email)) {
            return ResultJson.buildError("邮箱格式错误");
        }
        UserQuery userQuery = new UserQuery();
        userQuery.setUserName(email);
        User userBack = userMapper.selectBaseByUserName(userQuery);
        if (userBack == null) {
            return ResultJson.buildError("用户不存在");
        }
        //产生一个token
        String token = Base64.getEncoder().encodeToString(email.getBytes());
        String suffix = "?email=" + email + "&reset_password_token=" + token;
        String content = "<html> <body> " +
                "<img  src='cid:imageId'>" +
                "<p class=\"text-center\">亲爱的<span style=\"color: #0066FF;\">{nickName}</span></p>\n" +
                "        <p class=\"text-center\">小蜗为原创而生</p>\n" +
                "        <p class=\"text-center\">点击<a href=\"{path}\">www.com.cn</a>进行修改密码，<br/>就可以重新在原创的世界里游玩啦。</p>\n" +
                "        <p class=\"text-center\">期待你的精彩艺术人生！</p></body></html>";
        Map<String, String> map = new HashMap<>();
        map.put("nickName", userBack.getNickName());
        //TODO 环境部署以后  修改文件位置
//        map.put("path", "file:///E:\\2020\\test\\test\\swparent\\module_web\\src\\main\\resources\\static\\password.html");
        map.put("path", "http://www.baidu.com");
        try {
            String content1 = ToolsUtil.replaceTemplate(content, map);
            asyncService.sendImageEmailAsync(email, "忘记密码",
                    content1, "E:\\Users\\11930\\Desktop\\other\\SlowWormPainterProject (2)\\SlowWormPainterProject\\img\\ES-logo.png", "imageId");
        } catch (Exception e) {
            log.error("发送忘记密码邮件异常:{}", e.getMessage());
        }
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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultJson resetPassword(UserQuery userQuery) {
        if (StringUtils.isEmpty(userQuery.getEmail())) {
            return ResultJson.buildError("邮箱为空");
        }
        if (StringUtils.isEmpty(userQuery.getPassword())) {
            return ResultJson.buildError("密码为空");
        }
//        String token = RedisPoolUtil.get(RedisTimeConstant.EMAIL_CODE_RANDOM + userQuery.getEmail());
//        if (StringUtils.isEmpty(token) || token.equals(userQuery.getResetPasswordToken())) {
//            return ResultJson.buildError("该链接已经失效,请尝试重新发送密码重置邮件");
//        }
        UserBack userBack = userMapper.selectByEmail(userQuery);
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

    @Override
    public ResultJson isPainter() {
        int loginUserId = -1;
        try {
            loginUserId = SysConfigUtil.getLoginUserId();
        } catch (Exception e) {
            log.error("获取当前用户异常：{}", e.getMessage());
        }
        if (loginUserId <= 0) {
            return ResultJson.buildSuccess(false, "尚未认证画师");
        }
        User user = userMapper.selectByPrimaryKey(loginUserId);
        if ("Y".equals(user.getPainter())) {
            return ResultJson.buildSuccess(true, "已认证画师");
        }
        return ResultJson.buildSuccess(false, "尚未认证画师");
    }

    @Override
    public ResultJson chooseTags(String tags) {
        if (StringUtils.isEmpty(tags)) {
            return ResultJson.buildError("传入参数错误");
        }
        String[] split = tags.split("-");
        List<SysUserTag> list = new ArrayList<>();
        int userId = SysConfigUtil.getLoginUserId();
        for (int i = 0; i < split.length; i++) {
            SysUserTag userTag = new SysUserTag();
            userTag.setTagId(Integer.valueOf(split[i]));
            userTag.setUserId(userId);
            list.add(userTag);
        }
        userTagMapper.insertList(list);
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson allTags() {
        return ResultJson.buildSuccess(tagMapper.selectAll());
    }

    @Override
    public ResultJson getUserInfo(UserQuery userQuery) {
        if (userQuery == null || userQuery.getUserID() == null || userQuery.getUserID() <= 0) {
            userQuery = new UserQuery();
            userQuery.setUserID(SysConfigUtil.getLoginUserId());
        }
        UserBack userBack = userMapper.selectByUserName(userQuery);
        return ResultJson.buildSuccess(userBack);
    }


    //更新名片信息
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResultJson updateUserAttr(UserQuery userQuery) {
        if (userQuery.getUserID() == null || userQuery.getUserID() <= 0) {
            log.error("用户id为空");
            return ResultJson.buildError("用户id为空");
        }
        if (!StringUtils.isEmpty(userQuery.getUHistory())) {
            UserAttr userAttrFromDB = userAttrMapper.selectByUserId(userQuery.getUserID());
            userAttrFromDB.setUHistory(userQuery.getUHistory());
            userAttrFromDB.setUpdateTime(new Date());
            userAttrMapper.updateByPrimaryKeySelective(userAttrFromDB);
        }
        if (!StringUtils.isEmpty(userQuery.getCity())) {
            User user = userMapper.selectByPrimaryKey(userQuery.getUserID());
            user.setCity(userQuery.getCity());
            userMapper.updateByPrimaryKeySelective(user);
        }
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson updateUserHeadImage(String picString, Integer userId) {
        if (StringUtils.isEmpty(picString)) {
            return ResultJson.buildError("传入图片为空");
        }
        ResultJson resultJson = ftpService.uploadToServer(picString, false);
        if (ToolsUtil.verifyParams(resultJson)) {
//            List<SysImage> imageList = new ArrayList<>();
            Map<String, String> map = (Map<String, String>) resultJson.getData();
            SysImage image = new SysImage();
            image.setUrl(map.get("url"));
            image.setThumbUrl(map.get("thumbUrl"));
            image.setCreateTime(new Date());
            imageMapper.insertUseGeneratedKeys(image);
            log.info("上传头像成功，开始设置用户头像路径....");
            UserAttr userAttr = userAttrMapper.selectByUserId(userId);
            userAttr.setHeadImage(map.get("url"));
            userAttr.setUpdateTime(new Date());
            userAttrMapper.updateByPrimaryKeySelective(userAttr);
            log.info("设置用户头像路径成功");
//            imageList.add(image);
            return ResultJson.buildSuccess(image);
        } else {
            log.error(resultJson.toString());
            return resultJson;
        }
    }

    @Override
    public List<Integer> selectNormalUserIdList() {
        return userMapper.selectAllNormalUserIds();
    }
}
