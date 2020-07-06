package com.example.lixc.controller.web.checkManage;

import com.example.lixc.util.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/3 17:09
 */
@Api("作品审核失败")
@Slf4j
@RestController
@RequestMapping("/web/admin/check/workFail")
public class WorkCheckFailController {
    @ApiOperation("作品审核失败")
    @PostMapping("/workCheckFail")
    public ResultJson workCheckFail() {
        System.out.println("workCheckFail");
        return null;
    }
}
