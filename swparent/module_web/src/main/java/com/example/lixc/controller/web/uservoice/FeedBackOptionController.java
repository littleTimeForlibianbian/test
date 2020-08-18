package com.example.lixc.controller.web.uservoice;

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



    @ApiOperation("添加反馈优化")
    @PostMapping("/add")
    public ResultJson add(VersionSpoQuery sysVersionSpo) {
        try {
            sysVersionSpo.setType(UserVoiceTypeEnum.TYPE_VERSIONSPO.getCode());
            return service.add(sysVersionSpo);
        } catch (Exception e) {
            log.error("添加版本剧透 exception:{}", e.getMessage());
            return ResultJson.buildError("添加版本剧透异常");
        }
    }

    @ApiOperation("查询反馈优化")
    @PostMapping("/detail")
    public ResultJson detail(Integer id) {
        try {
            VersionSpoQuery versionSpoQuery = new VersionSpoQuery();
            versionSpoQuery.setId(id);
            return service.detail(versionSpoQuery);
        } catch (Exception e) {
            log.error("查询版本剧透列表 exception:{}", e.getMessage());
            return ResultJson.buildError("查询反馈优化详情异常");
        }
    }

    @ApiOperation("反馈优化编辑")
    @PostMapping("/edit")
    public ResultJson edit(VersionSpoQuery versionSpoQuery) {
        try {
            return service.edit(versionSpoQuery);
        } catch (Exception e) {
            log.error("查询版本剧透列表 exception:{}", e.getMessage());
            return ResultJson.buildError("查询反馈优化详情异常");
        }
    }

    @ApiOperation("反馈优化单个删除")
    @PostMapping("/deleteById")
    public ResultJson deleteById(Integer id) {
        try {
            return service.deleteById(id);
        } catch (Exception e) {
            log.error("反馈优化单个删除 exception:{}", e.getMessage());
            return ResultJson.buildError("反馈优化单个删除异常");
        }
    }

    @ApiOperation("反馈优化批量删除")
    @PostMapping("/deleteBatch")
    public ResultJson deleteBatch(String ids) {
        try {
            return service.deleteByBatch(ids);
        } catch (Exception e) {
            log.error("反馈优化批量删除 exception:{}", e.getMessage());
            return ResultJson.buildError("反馈优化批量删除异常");
        }
    }
}
