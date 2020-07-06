package com.example.lixc.service;

import com.example.lixc.entity.Tag;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.AdminUserBack;
import com.example.lixc.vo.query.AdminUserQuery;
import com.example.lixc.vo.query.UserQuery;
import com.github.pagehelper.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/1 13:51
 */
public interface UserPortalService {
    /*************portal start***********************/

    /**
     * 用户注册接口
     *
     * @param userQuery 用户对象
     * @return ResultJson
     */
    ResultJson registerUser(UserQuery userQuery);

    /**
     * 用户登录接口
     *
     * @param userQuery 用户对象
     * @return ResultJson
     */
    ResultJson Logon(UserQuery userQuery, HttpServletRequest request);


    /**
     * 用户注册激活接口
     *
     * @param param 用户昵称
     * @return ResultJson
     */
    ResultJson activeRegister(String param);

    /**
     * 忘记密码
     *
     * @return
     */
    ResultJson forgetPassword(String email);


    /**
     * 重置密码
     *
     * @param userQuery
     * @return
     */
    ResultJson resetPassword(UserQuery userQuery);


    ResultJson isPainter();


    /*************portal end***********************/

    /**
     *
     *
     *
     *
     *
     *
     */
    /**
     * 选择标签
     *
     * @param tags
     * @return
     */
    ResultJson chooseTags(String tags);

    ResultJson allTags();


    /*************web end***********************/
}
