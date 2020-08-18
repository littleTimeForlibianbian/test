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
 * @Description 与反馈优化 公用一个接口，通过type来进行区分
 * @createTime 2020/6/27 10:47
 */
@Api("版本剧透")
@Slf4j
@RestController
@RequestMapping("/web/manager/user/voice/VerSpo")
public class VersionSpoilersController {

    @Autowired
    private VersionSpoService service;


    @ApiOperation("添加版本剧透")
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

    @ApiOperation("查询版本剧透")
    @PostMapping("/detail")
    public ResultJson detail(VersionSpoQuery sysVersionSpo) {
        try {
            sysVersionSpo.setType(UserVoiceTypeEnum.TYPE_VERSIONSPO.getCode());
            return service.detail(sysVersionSpo);
        } catch (Exception e) {
            log.error("添加版本剧透 exception:{}", e.getMessage());
            return ResultJson.buildError("添加版本剧透异常");
        }
    }

    @ApiOperation("修改版本剧透")
    @PostMapping("/edit")
    public ResultJson edit(VersionSpoQuery sysVersionSpo) {
        try {
            sysVersionSpo.setType(UserVoiceTypeEnum.TYPE_VERSIONSPO.getCode());
            return service.edit(sysVersionSpo);
        } catch (Exception e) {
            log.error("修改版本剧透 exception:{}", e.getMessage());
            return ResultJson.buildError("修改版本剧透 异常");
        }
    }

    @ApiOperation("单个删除版本剧透")
    @PostMapping("/deleteById")
    public ResultJson deleteById(Integer id) {
        try {
            return service.deleteById(id);
        } catch (Exception e) {
            log.error("修改版本剧透 exception:{}", e.getMessage());
            return ResultJson.buildError("修改版本剧透 异常");
        }
    }

    @ApiOperation("批量删除版本剧透")
    @PostMapping("/deleteByBatch")
    public ResultJson deleteByBatch(String ids) {
        try {
            return service.deleteByBatch(ids);
        } catch (Exception e) {
            log.error("修改版本剧透 exception:{}", e.getMessage());
            return ResultJson.buildError("修改版本剧透 异常");
        }
    }

    @ApiOperation("查询版本剧透列表")
    @PostMapping("/selectForList")
    public Page<VersionSpoBack> selectForList(VersionSpoQuery sysVersionSpo) {
        try {
            sysVersionSpo.setType(UserVoiceTypeEnum.TYPE_VERSIONSPO.getCode());
            return service.selectForList(sysVersionSpo);
        } catch (Exception e) {
            log.error("查询版本剧透列表 exception:{}", e.getMessage());
            return new Page<>();
        }
    }
}
