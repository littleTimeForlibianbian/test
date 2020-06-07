package com.example.lixc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.lixc.entity.Code;
import com.example.lixc.entity.User;
import com.example.lixc.mapper.CodeMapper;
import com.example.lixc.mapper.SysConfigMapper;
import com.example.lixc.mapper.UserMapper;
import com.example.lixc.service.IAsyncService;
import com.example.lixc.service.IMailService;
import com.example.lixc.service.IUserService;
import com.example.lixc.util.RedisContents;
import com.example.lixc.util.RedisPoolUtil;
import com.example.lixc.util.ResultJson;
import com.example.lixc.util.ToolsUtil;
import io.netty.handler.codec.base64.Base64Encoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.Date;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/1 13:52
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CodeMapper codeMapper;

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Autowired
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
     * @param user
     * @return
     */
    public ResultJson registerUser(User user) {
        log.info("registerUser>>>输入参数：" + user.toString());
        if (!ToolsUtil.verifyParams(user.checkParams())) {
            log.info("用户注册】输入参数错误", JSON.toJSONString(user));
            return ResultJson.buildError("输入参数错误");
        }
        String isOpen = sysConfigMapper.selectAll().get(0).getInvitationCodeOpen();
        if ("Y".equals(isOpen)) {//校验邀请码
            Code code = new Code();
            code.setCode(user.getInvitationCode());
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
            if (codeFromDB.getCreateTime().before(DateUtils.addDays(new Date(), 0 - days))) {
                return ResultJson.buildError("邀请码已经过期，请联系邀请人重新生成");
            }
        }
        //校验手机号是否已经注册
        if (userMapper.existsWithPhone(user.getPhone()) > 0) {
            return ResultJson.buildError("手机号已经被注册");
        }
        //校验邮箱是否注册
        if (userMapper.existsWithEmail(user.getEmail()) > 0) {
            return ResultJson.buildError("邮箱已经被注册");
        }
        //添加用户
        userMapper.insertSelective(user);
        //根据用户的id的base64值发送邮件，增加一个邮件记录表
        String params = Base64.getEncoder().encodeToString(user.getNickName().getBytes());
        String email_content = "";
        asyncService.sendEmailAsync(user.getEmail(), subject, content);
        return ResultJson.buildSuccess();
    }


    /**
     * 用户登录
     *
     * @param user 用户对象
     * @return
     */
    public ResultJson Logon(User user) {
        if (StringUtils.isEmpty(user.getUserName())) {
            return ResultJson.buildError("用户名为空");
        }
        //使用用户名和密码进行登录
        User userFormDB = userMapper.selectByUserName(user);
        if (userFormDB != null) {
            log.info("登录成功");
            //存 redis
            RedisPoolUtil.set(RedisContents.USER_TOKEN + userFormDB.getUserName(), JSONObject.toJSONString(user), expireTime);
        }
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson getAllUsers() {
        return ResultJson.buildSuccess(userMapper.getAllUser());
    }

}
