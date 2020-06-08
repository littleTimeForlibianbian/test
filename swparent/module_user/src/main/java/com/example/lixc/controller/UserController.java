package com.example.lixc.controller;

import com.example.lixc.service.UserService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.query.UserQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixc
 * @Description 用户处理类
 * @createTime 2020/6/1 12:43
 */
@RestController
@RequestMapping("/user/normal")
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
    public ResultJson registerUser(UserQuery user) {
        return userService.registerUser(user);
    }

    /**
     * 注册激活接口
     *
     * @param param 昵称base64
     * @return
     */
    @RequestMapping("/activeRegister")
    public ResultJson activeRegister(String param) {
        return userService.activeRegister(param);
    }

    /**
     * 用戶登录接口
     *
     * @param userQuery 登录对象
     * @return
     */
    @PostMapping("/Logon")
    public ResultJson Logon(UserQuery userQuery) {
        return userService.Logon(userQuery);
    }


}
