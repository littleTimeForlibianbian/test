package com.example.lixc.service.impl;

import com.example.lixc.entity.SysReport;
import com.example.lixc.entity.SysVersionSpo;
import com.example.lixc.mapper.SysReportMapper;
import com.example.lixc.service.ReportService;
import com.example.lixc.util.ResultJson;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/10 16:08
 */
@Service
@Slf4j
public class ReportServiceImpl implements ReportService {


    @Autowired
    private SysReportMapper reportMapper;

    @Override
    public ResultJson deleteById(String id) {
        if (StringUtils.isEmpty(id)) {
            return ResultJson.buildError("传入参数对象id为空");
        }
        reportMapper.deleteByPrimaryKey(id);
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson deleteByBatch(String ids) {
        if (StringUtils.isEmpty(ids)) {
            return ResultJson.buildError("传入参数为空");
        }
        try {
            reportMapper.delByIds(ids.split(","));
        } catch (Exception e) {
            log.error("deleteByBatch exception: {}", e.getMessage());
            return ResultJson.buildError(e.getMessage());
        }
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson edit(SysReport sysReport) {
        if (sysReport.getId() < 0) {
            return ResultJson.buildError("id为空");
        }
        reportMapper.updateByPrimaryKeySelective(sysReport);
        return ResultJson.buildSuccess();
    }

    @Override
    public ResultJson detail(SysReport sysReport) {
        if (sysReport.getId() < 0) {
            return ResultJson.buildError("id为空");
        }
        return ResultJson.buildSuccess(reportMapper.selectByPrimaryKey(sysReport));
    }

    @Override
    public ResultJson add(SysReport sysReport) {
        if (StringUtils.isEmpty(sysReport.getContent())) {
            return ResultJson.buildError("举报条件为空");
        }
        sysReport.setCreateTime(new Date());
        reportMapper.insertSelective(sysReport);
        return ResultJson.buildSuccess();
    }

    @Override
    public List<SysReport> selectForList(SysReport sysReport) {
        return reportMapper.selectAll();
    }
}
