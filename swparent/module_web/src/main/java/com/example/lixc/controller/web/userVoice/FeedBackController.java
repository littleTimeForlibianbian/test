package com.example.lixc.controller.web.userVoice;

import com.example.lixc.service.FeedbackService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.query.SuggestQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/27 10:39
 */
@Api("建议反馈")
@RestController
@RequestMapping("/web/manager/user/voice/feedback")
public class FeedBackController {

    @Autowired
    private FeedbackService service;

    @ApiOperation("建议反馈列表集合")
    @PostMapping("/selectForList")
    public ResultJson selectForList(SuggestQuery suggestQuery) {
        return ResultJson.buildSuccess(service.selectForList(suggestQuery));
    }

    @ApiOperation("建议反馈单个删除")
    @PostMapping("/deleteById")
    public ResultJson deleteById(SuggestQuery suggestQuery) {
        return service.deleteById(suggestQuery);
    }

    @ApiOperation("建议反馈点赞")
    @PostMapping("/like")
    public ResultJson like(SuggestQuery suggestQuery) {
        return service.like(suggestQuery);
    }

    @ApiOperation("建议反馈批量删除")
    @PostMapping("/deleteBatch")
    public ResultJson deleteBatch(String ids) {
        return service.deleteBatch(ids);
    }

}
