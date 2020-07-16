package com.example.lixc.controller.web.ArticleManage;

import com.example.lixc.entity.SysMessage;
import com.example.lixc.service.MessageService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.query.MessageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wilson
 * @Description
 * @createTime 2020/6/27 10:53
 */
@Api("系统消息")
@Slf4j
@RestController
@RequestMapping("/web/manager/article")
public class SystemMessageController {
    @Autowired
    private MessageService messageService;

    @ApiOperation("查询列表")
    @PostMapping("/selectForList")
    public ResultJson selectForList(MessageQuery messageQuery) {
        //查询系统消息列表
        return null;
    }

    @ApiOperation("添加")
    @PostMapping("/add")
    public ResultJson add(MessageQuery messageQuery) {
        try {
            messageService.create(messageQuery);
            return ResultJson.buildSuccess();
        } catch (Exception e) {
            log.error("添加消息异常:{}", e.getMessage());
            return ResultJson.buildError("添加消息异常");
        }
    }

    @ApiOperation("详情")
    @PostMapping("/detail")
    public ResultJson detail(MessageQuery messageQuery) {
        SysMessage message = messageService.queryMessage(messageQuery);
        return ResultJson.buildSuccess(message);
    }


    @ApiOperation("单个删除")
    @PostMapping("/deleteById")
    public ResultJson deleteById(MessageQuery messageQuery) {
        //删除系统消息
        try {
            messageService.deleteById(messageQuery);
            return ResultJson.buildSuccess();
        } catch (Exception e) {
            log.error("添加消息异常:{}", e.getMessage());
            return ResultJson.buildError("添加消息异常");
        }
    }


    @ApiOperation("批量删除")
    @PostMapping("/deleteByBatch")
    public ResultJson deleteByBatch(String ids) {
        return null;
    }


}
