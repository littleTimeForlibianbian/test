package com.example.lixc.controller.web.porotalManage;

import com.example.lixc.util.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.ListUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/27 12:10
 */
@Api("身份审核")
@RestController
@RequestMapping("/web/admin/policy/")
public class PolicyController {

    @ApiOperation("添加协议")
    @PostMapping("/add")
    public ResultJson add() {
        return null;
    }

}
