package com.example.lixc.controller.web.userVoice;

import com.example.lixc.util.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/27 10:45
 */
@Api("反馈优化")
@RestController
@RequestMapping("/web/manager/user/voice/")
public class FeedBackOptionController {


    @ApiOperation("反馈优化列表集合")
    @PostMapping("/selectForList")
    public ResultJson selectForList() {
        System.out.println("反馈优化列表集合");
        return null;
    }
    @ApiOperation("查询反馈优化")
    @PostMapping("/detail")
    public ResultJson detail() {
        return null;
    }

    @ApiOperation("反馈优化编辑")
    @PostMapping("/edit")
    public ResultJson edit() {
        System.out.println("列表集合");
        return null;
    }

    @ApiOperation("反馈优化单个删除")
    @PostMapping("/deleteById")
    public ResultJson deleteById() {
        System.out.println("反馈优化单个删除");
        return null;
    }

    @ApiOperation("反馈优化批量删除")
    @PostMapping("/deleteBatch")
    public ResultJson deleteBatch(String ids) {
        System.out.println("反馈优化批量删除");
        return null;
    }
}
