package com.example.lixc.controller.portal;

import com.example.lixc.service.UserService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.query.UserQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lixc
 * @Description 用户处理类
 * @createTime 2020/6/1 12:43
 */
@Api("用户管理类")
@RestController
@RequestMapping("/public/user")
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

    @GetMapping("/test")
    public ResultJson test() {
        return ResultJson.buildSuccess("1234");
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
    public ResultJson Logon(@RequestBody UserQuery userQuery, HttpServletRequest request) {
        try {
            return userService.Logon(userQuery, request);
        } catch (Exception e) {
            log.error("用户登录发生异常:{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("用户登录发生异常");
        }
    }


    /**
     * 忘记密码接口
     *
     * @return
     */
    @PostMapping("/forgetPassword")
    @ApiOperation("用户登录")
    public ResultJson forgetPassword(String email) {
        try {
            return userService.forgetPassword(email);
        } catch (Exception e) {
            log.error("忘记密码发送邮件异常:{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("忘记密码发送邮件异常");
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

    /**
     * 查询当前用户是否是画师，只有画师才能看到myWorld
     *
     * @return
     */
    @PostMapping("/isPainter")
    @ApiOperation("是否是画师")
    public ResultJson isPainter() {
        try {
            return userService.isPainter();
        } catch (Exception e) {
            log.error("重置密码发生异常:{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("重置密码发生异常");
        }
    }


}
