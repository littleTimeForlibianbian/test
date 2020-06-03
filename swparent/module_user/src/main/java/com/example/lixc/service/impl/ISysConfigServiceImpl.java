package com.example.lixc.service.impl;

import com.example.lixc.mapper.SysConfigMapper;
import com.example.lixc.service.ISysConfigService;
import com.example.lixc.util.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ISysConfigServiceImpl implements ISysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public ResultJson getSysConfig() {
        return ResultJson.buildSuccess(sysConfigMapper.selectAll());
    }
}
