package com.example.lixc.service;

import com.example.lixc.entity.User;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.AdminUserBack;
import com.example.lixc.vo.query.AdminUserQuery;
import com.example.lixc.vo.query.UserQuery;
import com.github.pagehelper.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/1 13:51
 */
public interface UserService {
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


    /*************portal end***********************/

    /**
     *
     *
     *
     *
     *
     *
     */
    /*************web start***********************/
    /**
     * 获取所有的管理员用户
     *
     * @param adminUserQuery
     * @return
     */
    Page<AdminUserBack> selectAdminUsers(AdminUserQuery adminUserQuery);


    /**
     * 添加管理员
     *
     * @param adminUserQuery
     * @return
     */
    ResultJson addAdminUser(AdminUserQuery adminUserQuery);

    /**
     * 查询管理员详情
     *
     * @param adminUserQuery
     * @return
     */
    ResultJson detailAdminUser(AdminUserQuery adminUserQuery);

    /**
     * 更新管理员  用户名不能更新
     *
     * @param adminUserQuery
     * @return
     */
    ResultJson updateAdminUser(AdminUserQuery adminUserQuery);

    /**
     * 单个删除管理员
     *
     * @param adminUserQuery
     * @return
     */
    ResultJson delAdminUser(AdminUserQuery adminUserQuery);

    /**
     * 批量删除管理员
     *
     * @param ids
     * @return
     */
    ResultJson delAdminUserBatch(String ids);


    /*************web end***********************/
}
