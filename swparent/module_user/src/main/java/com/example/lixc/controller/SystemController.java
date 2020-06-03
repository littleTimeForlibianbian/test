package com.example.lixc.controller;

import com.example.lixc.service.ISysConfigService;
import com.example.lixc.util.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys")
public class SystemController {

    @Autowired
    private ISysConfigService configService;

    @RequestMapping("/getSysConfig")
    public ResultJson getSysConfig() {
        return configService.getSysConfig();
    }
}
