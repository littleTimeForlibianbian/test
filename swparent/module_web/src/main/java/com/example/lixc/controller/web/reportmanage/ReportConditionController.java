package com.example.lixc.controller.web.reportmanage;

import com.example.lixc.entity.SysReport;
import com.example.lixc.service.ReportService;
import com.example.lixc.util.ResultJson;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/27 11:06
 */
@Api("作品举报条件")
@Slf4j
@RestController
@RequestMapping("/web/manager/report")
public class ReportConditionController {

    @Autowired
    private ReportService reportService;

    /**
     * 默认查询全部
     *
     * @param sysReport
     * @return
     */
    @ApiOperation("查询列表")
    @PostMapping("/selectForList")
    public ResultJson selectForList(SysReport sysReport) {
        List<SysReport> sysReports = reportService.selectForList(sysReport);
        try {
            return ResultJson.buildSuccess(sysReports);
        } catch (Exception e) {
            log.error("查询列表异常：{}", e.getMessage());
            return ResultJson.buildError("查询举报列表异常");
        }
    }

    @ApiOperation("添加")
    @PostMapping("/add")
    public ResultJson add(SysReport sysReport) {
        try {
            return reportService.add(sysReport);
        } catch (Exception e) {
            log.error("添加异常：{}", e.getMessage());
            return ResultJson.buildError("添加异常");
        }
    }

    @ApiOperation("详情")
    @PostMapping("/detail")
    public ResultJson detail(SysReport sysReport) {
        return reportService.detail(sysReport);
    }

    @ApiOperation("编辑")
    @PostMapping("/edit")
    public ResultJson edit(SysReport sysReport) {
        try {
            return reportService.edit(sysReport);
        } catch (Exception e) {
            log.error("添加异常：{}", e.getMessage());
            return ResultJson.buildError("添加异常");
        }
    }


    @ApiOperation("单个删除")
    @PostMapping("/deleteById")
    public ResultJson deleteById(String id) {
        try {
            return reportService.deleteById(id);
        } catch (Exception e) {
            log.error("添加异常：{}", e.getMessage());
            return ResultJson.buildError("添加异常");
        }
    }


    @ApiOperation("批量删除")
    @PostMapping("/deleteByBatch")
    public ResultJson deleteByBatch(String ids) {
        try {
            return reportService.deleteByBatch(ids);
        } catch (Exception e) {
            log.error("添加异常：{}", e.getMessage());
            return ResultJson.buildError("添加异常");
        }
    }

}
