package com.example.lixc.controller.web.usermanage;

import com.example.lixc.service.SelectUserService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.query.UserQuery;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("无认证身份管理类")
@Slf4j
@RestController
@RequestMapping("/web/manager/user/noAuth")
public class NoAuthController {

    @Autowired
    private SelectUserService selectUserService;

    @ApiOperation("查询列表")
    @PostMapping("/selectForList")
    public Page<UserBack> selectForList(UserQuery userQuery) {
        try {
            if (StringUtils.isEmpty(userQuery.getPainter())) {
                userQuery.setPainter("N");
            }
            return selectUserService.selectForList(userQuery);
        } catch (Exception e) {
            log.error("查询列表异常：{}", e.getMessage());
            return new Page<>();
        }
    }


    @ApiOperation("更改账号状态")
    @PostMapping("/enableUser")
    public ResultJson enableUser(UserQuery userQuery) {
        try {
            return selectUserService.enableUser(userQuery);
        } catch (Exception e) {
            log.error("更改账号状态异常：{}", e.getMessage());
        }
        return ResultJson.buildError("更改账号状态异常");
    }

}
