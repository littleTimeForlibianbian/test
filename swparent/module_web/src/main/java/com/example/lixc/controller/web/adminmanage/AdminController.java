package com.example.lixc.controller.web.adminmanage;

import com.example.lixc.service.UserAdminService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.AdminUserBack;
import com.example.lixc.vo.query.AdminUserQuery;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/web/admin")
public class AdminController {

    @Autowired
    private UserAdminService userAdminService;

    /**
     * 获取所有的管理员用户
     *
     * @return
     */
    @ApiOperation(value = "获取所有的管理员")
    @PostMapping(value = "/getAllAdminUser")
    public Page<AdminUserBack> getAllAdminUser(AdminUserQuery adminUserQuery) {
        return userAdminService.selectAdminUsers(adminUserQuery);
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
        return userAdminService.addAdminUser(adminUserQuery);
    }

    /**
     * 添加管理员
     *
     * @param adminUserQuery
     * @return
     */
    @ApiOperation(value = "更新管理员密码")
    @PostMapping("/updatePassword")
    public ResultJson updatePassword(AdminUserQuery adminUserQuery) {
        return userAdminService.updatePassword(adminUserQuery);
    }

    /**
     * 查询管理员详情
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "查询管理员详情")
    @PostMapping("/detailAdminUser")
    public ResultJson detailAdminUser(Integer userId) {
        return userAdminService.detailAdminUser(userId);
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
        return userAdminService.updateAdminUser(adminUserQuery);
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
        return userAdminService.delAdminUser(adminUserQuery);
    }

    /**
     * 单个删除管理员
     *
     * @param adminUserQuery
     * @return
     */
    @ApiOperation(value = "停用启用管理员")
    @PostMapping("/enable")
    public ResultJson enable(AdminUserQuery adminUserQuery) {
        try {
            return userAdminService.enable(adminUserQuery);
        } catch (Exception e) {
            log.error("停用启用管理员:{}", e.getMessage());
            return ResultJson.buildError("停用启用管理员异常");
        }
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
        return userAdminService.delAdminUserBatch(ids);
    }

}
