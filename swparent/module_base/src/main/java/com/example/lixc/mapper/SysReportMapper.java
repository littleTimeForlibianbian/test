package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.SysReport;

public interface SysReportMapper extends SwBaseMapper<SysReport> {
    void delByIds(String[] split);
}