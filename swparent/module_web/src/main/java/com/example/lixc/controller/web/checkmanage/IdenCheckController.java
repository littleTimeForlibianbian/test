package com.example.lixc.controller.web.checkmanage;

import com.example.lixc.service.IdenCheckService;
import com.example.lixc.util.ResultJson;
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
 * @createTime 2020/6/27 10:10
 */
@Api("身份审核")
@Slf4j
@RestController
@RequestMapping("/web/manager/check/iden")
public class IdenCheckController {


    @Autowired
    private IdenCheckService checkService;

    @ApiOperation("身份审核")
    @PostMapping("/identifyCheck")
    public ResultJson identifyCheck(UserQuery userQuery) {

        try {
            return checkService.identifyCheck(userQuery);
        } catch (Exception e) {
            log.error("【审核管理】身份审核异常：{}", e.getMessage());
            e.printStackTrace();
            return ResultJson.buildError("身份审核失败");
        }
    }

    @ApiOperation("查询列表")
    @PostMapping("/list")
    public Page<UserBack> list(UserQuery userQuery) {
        //param为用户名或者邮箱
        //查询所有  非画师的 并且状态为提交申请的
        return checkService.list(userQuery);
    }

}
