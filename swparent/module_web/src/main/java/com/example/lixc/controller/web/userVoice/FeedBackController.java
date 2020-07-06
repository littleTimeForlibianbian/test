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
 * @createTime 2020/6/27 10:39
 */
@Api("建议反馈")
@RestController
@RequestMapping("/web/manager/user/voice/")
public class FeedBackController {

    @ApiOperation("建议反馈列表集合")
    @PostMapping("/selectForList")
    public ResultJson selectForList() {
        System.out.println("建议反馈列表集合");
        return null;
    }

    @ApiOperation("建议反馈单个删除")
    @PostMapping("/deleteById")
    public ResultJson deleteById() {
        System.out.println("建议反馈单个删除");
        return null;
    }

    @ApiOperation("建议反馈批量删除")
    @PostMapping("/deleteBatch")
    public ResultJson deleteBatch(String ids) {
        System.out.println("建议反馈批量删除");
        return null;
    }

}
