package com.example.lixc.service.impl;

import com.example.lixc.mapper.SysConfigMapper;
import com.example.lixc.service.SysConfigService;
import com.example.lixc.util.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Resource
    private SysConfigMapper sysConfigMapper;

    @Override
    public ResultJson getSysConfig() {
        return ResultJson.buildSuccess(sysConfigMapper.selectAll());
    }
}
