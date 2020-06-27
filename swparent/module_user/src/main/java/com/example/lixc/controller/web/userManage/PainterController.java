package com.example.lixc.controller.web.userManage;

import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.query.UserQuery;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("画师身份管理类")
@RestController
@RequestMapping("/web/manager/user/painter")
public class PainterController {

    @ApiOperation("查询列表")
    @RequestMapping("/selectForList")
    Page<UserBack> selectForList(UserQuery userQuery) {
        return null;
    }


    @ApiOperation("更改账号状态")
    @RequestMapping("/enable")
    Page<UserBack> enable(UserQuery userQuery) {
        return null;
    }


    @ApiOperation("查询画师风格")
    @RequestMapping("/detail")
    Page<UserBack> detail(UserQuery userQuery) {
        return null;
    }
}
