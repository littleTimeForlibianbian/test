package com.example.lixc.controller;

import com.example.lixc.service.CodeService;
import com.example.lixc.util.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/1 14:49
 */
@RestController
@RequestMapping("/user/code")
@Slf4j
@Api("邀请码生成类")
public class CodeController {


    @Autowired
    private CodeService codeService;

    /**
     * 生成邀请码
     *
     * @return
     */
    @ApiOperation("生成邀请码")
    @PostMapping("/genInvitationCode")
    public ResultJson genInvitationCode() {
        return codeService.genInvitationCode();
    }

    /**
     * 生成二维码
     *
     * @return
     */
    public String genQrCode() {
        return null;
    }


}






