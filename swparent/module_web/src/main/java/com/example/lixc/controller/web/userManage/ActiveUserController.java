package com.example.lixc.controller.web.userManage;

import com.example.lixc.common.PageData;
import com.example.lixc.service.SelectUserService;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.query.UserQuery;
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
 * @createTime 2020/6/27 10:09
 */
@Api("活跃用户")
@Slf4j
@RestController
@RequestMapping("/web/manager/user/active")
public class ActiveUserController {
    @Autowired
    private SelectUserService selectUserService;

    @ApiOperation("查询活跃用户列表")
    @PostMapping("/selectForList")
    public PageData<UserBack> selectForList(UserQuery userQuery) {
        return selectUserService.selectForActiveList(userQuery);
    }
}
