package com.example.lixc.controller.web.userVoice;

import com.example.lixc.enums.UserVoiceTypeEnum;
import com.example.lixc.service.VersionSpoService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.VersionSpoBack;
import com.example.lixc.vo.query.VersionSpoQuery;
import com.github.pagehelper.Page;
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
 * @createTime 2020/6/27 10:45
 */
@Api("反馈优化")
@Slf4j
@RestController
@RequestMapping("/web/manager/user/voice/feedbackOpt")
public class FeedBackOptionController {

    @Autowired
    private VersionSpoService service;

    @ApiOperation("反馈优化列表集合")
    @PostMapping("/selectForList")
    public Page<VersionSpoBack> selectForList(VersionSpoQuery sysVersionSpo) {
        try {
            sysVersionSpo.setType(UserVoiceTypeEnum.TYPE_FEEDBACKOPT.getCode());
            return service.selectForList(sysVersionSpo);
        } catch (Exception e) {
            log.error("查询版本剧透列表 exception:{}", e.getMessage());
            return new Page<>();
        }
    }

    @ApiOperation("查询反馈优化")
    @PostMapping("/detail")
    public ResultJson detail() {
        return null;
    }

    @ApiOperation("反馈优化编辑")
    @PostMapping("/edit")
    public ResultJson edit() {
        System.out.println("列表集合");
        return null;
    }

    @ApiOperation("反馈优化单个删除")
    @PostMapping("/deleteById")
    public ResultJson deleteById() {
        System.out.println("反馈优化单个删除");
        return null;
    }

    @ApiOperation("反馈优化批量删除")
    @PostMapping("/deleteBatch")
    public ResultJson deleteBatch(String ids) {
        System.out.println("反馈优化批量删除");
        return null;
    }
}
