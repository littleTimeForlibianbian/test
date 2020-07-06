package com.example.lixc.controller.web.reportManage;

import com.example.lixc.util.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/27 11:06
 */
@Api("作品举报条件")
@RestController
@RequestMapping("/web/admin/report/")
public class ReportConditionController {

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

    @ApiOperation("编辑")
    @PostMapping("/edit")
    public ResultJson edit() {
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
