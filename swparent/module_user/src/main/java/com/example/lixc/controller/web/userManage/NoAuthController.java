package com.example.lixc.controller.web.userManage;

import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.query.UserQuery;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("无认证身份管理类")
@RestController
@RequestMapping("/web/manager/user/noAuth")
public class NoAuthController {

    @ApiOperation("查询列表")
    @RequestMapping("/selectForList")
    Page<UserBack> selectForList(UserQuery userQuery) {
        return null;
    }


    @ApiOperation("更改账号状态")
    @RequestMapping("/enableUser")
    Page<UserBack> enableUser(UserQuery userQuery) {
        return null;
    }


    @ApiOperation("删除用户")
    @RequestMapping("/delete")
    Page<UserBack> delete(UserQuery userQuery) {
        return null;
    }

    @ApiOperation("批量删除")
    @RequestMapping("/deleteBatch")
    Page<UserBack> deleteBatch(String ids) {
        return null;
    }


}
