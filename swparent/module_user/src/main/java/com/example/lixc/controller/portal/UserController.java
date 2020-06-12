package com.example.lixc.controller.portal;

import com.example.lixc.service.UserService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.query.UserQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lixc
 * @Description 用户处理类
 * @createTime 2020/6/1 12:43
 */
@Api("用户管理类")
@RestController
@RequestMapping("/portal/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 用户注册接口
     *
     * @param user
     * @return
     */
    @PostMapping("/registerUser")
    @ApiOperation("用户注册")
    public ResultJson registerUser(UserQuery user) {
        try {
            return userService.registerUser(user);
        } catch (Exception e) {
            log.error("用户注册发生异常:{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("用户注册发生异常");
        }

    }

    /**
     * 注册激活接口
     *
     * @param param 昵称base64
     * @return
     */
    @PostMapping("/activeRegister")
    @ApiOperation("用户注册-激活")
    public ResultJson activeRegister(String param) {
        try {
            return userService.activeRegister(param);
        } catch (Exception e) {
            log.error("用户激活发生异常:{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("用户激活发生异常");
        }
    }

    /**
     * 用戶登录接口
     *
     * @param userQuery 登录对象
     * @return
     */
    @PostMapping("/Logon")
    @ApiOperation("用户登录")
    public ResultJson Logon(UserQuery userQuery, HttpServletRequest request) {
        try {
            return userService.Logon(userQuery, request);
        } catch (Exception e) {
            log.error("用户登录发生异常:{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("用户登录发生异常");
        }
    }


    @PostMapping("/resetPassword")
    @ApiOperation("忘记密码")
    public ResultJson resetPassword(UserQuery userQuery) {
        try {
            return userService.resetPassword(userQuery);
        } catch (Exception e) {
            log.error("重置密码发生异常:{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("重置密码发生异常");
        }
    }


}
