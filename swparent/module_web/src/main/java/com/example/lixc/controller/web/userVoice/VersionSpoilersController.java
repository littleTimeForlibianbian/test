package com.example.lixc.controller.web.userVoice;

import com.example.lixc.util.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/27 10:47
 */
@Api("版本剧透")
@RestController
@RequestMapping("/web/manager/user/voice/")
public class VersionSpoilersController {

    @ApiOperation("添加版本剧透")
    @PostMapping("/add")
    public ResultJson add() {
        return null;
    }

    @ApiOperation("查询版本剧透")
    @PostMapping("/detail")
    public ResultJson detail() {
        return null;
    }

    @ApiOperation("修改版本剧透")
    @PostMapping("/edit")
    public ResultJson edit() {
        return null;
    }

    @ApiOperation("单个删除版本剧透")
    @PostMapping("/deleteById")
    public ResultJson deleteById(String id) {
        return null;
    }

    @ApiOperation("批量删除版本剧透")
    @PostMapping("/deleteById")
    public ResultJson deleteByBatch(String id) {
        return null;
    }
}
