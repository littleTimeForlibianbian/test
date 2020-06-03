package com.example.lixc.controller;

import com.example.lixc.service.ICodeService;
import com.example.lixc.util.ToolsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/1 14:49
 */
@RestController
@RequestMapping("/code")
@Slf4j
public class CodeController {


    @Autowired
    private ICodeService codeService;

    /**
     * 生成邀请码
     *
     * @return
     */
    @RequestMapping("/genInvitationCode")
    public String genInvitationCode() {
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
