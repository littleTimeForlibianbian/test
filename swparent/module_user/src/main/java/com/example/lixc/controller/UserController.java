package com.example.lixc.controller;

import com.example.lixc.entity.User;
import com.example.lixc.service.IUserService;
import com.example.lixc.util.ResultJson;
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
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;


    /**
     * 用户注册接口
     *
     * @param user
     * @return
     */
    @PostMapping("/registerUser")
    public ResultJson registerUser(User user) {
        return userService.registerUser(user);
    }

    /**
     * 用戶登录接口
     * @param user  登录对象
     * @return
     */
    @PostMapping("/Logon")
    public ResultJson Logon(User user) {
        return userService.Logon(user);
    }
}
