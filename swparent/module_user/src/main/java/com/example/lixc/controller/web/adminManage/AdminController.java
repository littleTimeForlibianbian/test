package com.example.lixc.controller.web.adminManage;

import com.example.lixc.service.UserService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.AdminUserBack;
import com.example.lixc.vo.query.AdminUserQuery;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/7 19:39
 */
@Api("后台管理员管理类")
@RestController
@RequestMapping("/web/manager/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    /**
     * 获取所有的管理员用户
     *
     * @return
     */
    @ApiOperation(value = "获取所有的管理员")
    @PostMapping(value = "/getAllAdminUser")
    public Page<AdminUserBack> getAllAdminUser(AdminUserQuery adminUserQuery) {
        return userService.selectAdminUsers(adminUserQuery);
    }

    /**
     * 添加管理员
     *
     * @param adminUserQuery
     * @return
     */
    @ApiOperation(value = "添加管理员")
    @PostMapping("/addAdminUser")
    public ResultJson addAdminUser(AdminUserQuery adminUserQuery) {
        return userService.addAdminUser(adminUserQuery);
    }

    /**
     * 查询管理员详情
     *
     * @param adminUserQuery
     * @return
     */
    @ApiOperation(value = "查询管理员详情")
    @PostMapping("/detailAdminUser")
    public ResultJson detailAdminUser(AdminUserQuery adminUserQuery) {
        return userService.detailAdminUser(adminUserQuery);
    }

    /**
     * 更新管理员
     *
     * @param adminUserQuery
     * @return
     */
    @ApiOperation(value = "更新管理员")
    @PostMapping("/updateAdminUser")
    public ResultJson updateAdminUser(AdminUserQuery adminUserQuery) {
        return userService.updateAdminUser(adminUserQuery);
    }

    /**
     * 单个删除管理员
     *
     * @param adminUserQuery
     * @return
     */
    @ApiOperation(value = "单个删除管理员")
    @PostMapping("/delAdminUser")
    public ResultJson delAdminUser(AdminUserQuery adminUserQuery) {
        return userService.delAdminUser(adminUserQuery);
    }

    /**
     * 批量删除管理员
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除管理员")
    @PostMapping("/delAdminUserBatch")
    public ResultJson delAdminUserBatch(String ids) {
        return userService.delAdminUserBatch(ids);
    }

}
