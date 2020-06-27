package com.example.lixc.controller.web.checkManage;

import com.example.lixc.util.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/27 10:10
 */
@Api("身份审核")
@RestController
@RequestMapping("/web/admin/check/")
public class CheckController {

    @ApiOperation("身份审核")
    @PostMapping("/identifyCheck")
    public ResultJson identifyCheck() {
        System.out.println("identifyCheck");
        return null;
    }

    @ApiOperation("作品审核")
    @PostMapping("/workCheck")
    public ResultJson workCheck() {
        System.out.println("workCheck");
        return null;
    }


    @ApiOperation("作品审核失败")
    @PostMapping("/workCheckFail")
    public ResultJson workCheckFail() {
        System.out.println("workCheckFail");
        return null;
    }
}
