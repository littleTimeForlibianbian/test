package com.example.lixc.controller;

import com.example.lixc.service.UserService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.AdminUserBack;
import com.example.lixc.vo.query.AdminUserQuery;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/7 19:39
 */
@RestController
@RequestMapping("/user/manager/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    /**
     * 获取所有的管理员用户
     *
     * @return
     */
    @RequestMapping("/getAllAdminUser")
    public Page<AdminUserBack> getAllAdminUser(AdminUserQuery adminUserQuery) {
        return userService.selectAdminUsers(adminUserQuery);
    }

    /**
     * 添加管理员
     *
     * @param adminUserQuery
     * @return
     */
    @RequestMapping("/addAdminUser")
    public ResultJson addAdminUser(AdminUserQuery adminUserQuery) {
        return userService.addAdminUser(adminUserQuery);
    }

    /**
     * 查询管理员详情
     *
     * @param adminUserQuery
     * @return
     */
    @RequestMapping("/detailAdminUser")
    public ResultJson detailAdminUser(AdminUserQuery adminUserQuery) {
        return userService.detailAdminUser(adminUserQuery);
    }

    /**
     * 更新管路员
     *
     * @param adminUserQuery
     * @return
     */
    @RequestMapping("/updateAdminUser")
    public ResultJson updateAdminUser(AdminUserQuery adminUserQuery) {
        return userService.updateAdminUser(adminUserQuery);
    }

    /**
     * 单个删除管理员
     *
     * @param adminUserQuery
     * @return
     */
    @RequestMapping("/delAdminUser")
    public ResultJson delAdminUser(AdminUserQuery adminUserQuery) {
        return userService.delAdminUser(adminUserQuery);
    }

    /**
     * 批量删除管理员
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delAdminUserBatch")
    public ResultJson delAdminUserBatch(String ids) {
        return userService.delAdminUserBatch(ids);
    }

}
