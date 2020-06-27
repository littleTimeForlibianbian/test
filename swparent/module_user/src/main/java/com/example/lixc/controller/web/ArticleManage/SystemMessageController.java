package com.example.lixc.controller.web.ArticleManage;

import com.example.lixc.util.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/27 10:53
 */
@Api("系统消息")
@RestController
@RequestMapping("/web/manager/article")
public class SystemMessageController {


    @ApiOperation("查询列表")
    @PostMapping("/selectForList")
    public ResultJson selectForList() {
        return null;
    }

    @ApiOperation("添加")
    @PostMapping("/add")
    public ResultJson add() {
        return null;
    }

    @ApiOperation("详情")
    @PostMapping("/detail")
    public ResultJson detail() {
        return null;
    }


    @ApiOperation("单个删除")
    @PostMapping("/deleteById")
    public ResultJson deleteById() {
        return null;
    }


    @ApiOperation("批量删除")
    @PostMapping("/deleteByBatch")
    public ResultJson deleteByBatch() {
        return null;
    }


}
