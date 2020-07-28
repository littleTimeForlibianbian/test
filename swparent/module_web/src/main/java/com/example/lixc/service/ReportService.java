package com.example.lixc.service;

import com.example.lixc.entity.SysReport;
import com.example.lixc.util.ResultJson;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/10 16:00
 */
public interface ReportService {
    ResultJson deleteById(String id);

    ResultJson deleteByBatch(String ids);

    ResultJson edit(SysReport sysReport);

    ResultJson detail(SysReport sysReport);

    ResultJson add(SysReport sysReport);

    List<SysReport> selectForList(SysReport sysReport);
}
