package com.example.lixc.service;

import com.example.lixc.entity.User;
import com.example.lixc.util.ResultJson;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/1 13:51
 */
public interface IUserService {

    ResultJson registerUser(User user);

//    ResultJson selectAllNormalUsers();
//
//    ResultJson selectAllAdminUsers();

    ResultJson Logon(User user);

    ResultJson getAllUsers();
}
