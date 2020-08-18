package com.example.lixc.controller.web.articlemanage;

import com.example.lixc.service.MessageService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.MessageBack;
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
 * @Description 系统消息管理类
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
        try {
            return ResultJson.buildSuccess(messageService.querySysMessage(messageQuery));
        } catch (Exception e) {
            log.error("查询系统消息列表异常：{}", e.getLocalizedMessage());
            return ResultJson.buildError("查询系统消息列表异常");
        }
    }

    @ApiOperation("添加")
    @PostMapping("/add")
    public ResultJson add(MessageQuery messageQuery) {
        try {
            //只添加 不发送
            return messageService.addMessage(messageQuery);
        } catch (Exception e) {
            log.error("添加消息异常:{}", e.getMessage());
            return ResultJson.buildError("添加消息异常");
        }
    }


    @ApiOperation("详情")
    @PostMapping("/detail")
    public ResultJson detail(Integer messageId) {
        try {
            MessageBack message = messageService.queryMessage(messageId);
            return ResultJson.buildSuccess(message);
        } catch (Exception e) {
            log.error("查询消息详情异常:{}", e.getLocalizedMessage());
            return ResultJson.buildError("查询消息详情异常");
        }

    }

    @ApiOperation("编辑系统消息")
    @PostMapping("/edit")
    public ResultJson edit(MessageQuery messageQuery) {
        try {
            return messageService.edit(messageQuery);
        } catch (Exception e) {
            log.error("编辑系统消息异常:{}", e.getLocalizedMessage());
            return ResultJson.buildError("编辑系统消息异常");
        }

    }


    @ApiOperation("单个删除")
    @PostMapping("/deleteById")
    public ResultJson deleteById(Integer id) {
        //删除系统消息
        try {
            return messageService.deleteById(id);
        } catch (Exception e) {
            log.error("删除消息异常:{}", e.getMessage());
            return ResultJson.buildError("添加消息异常");
        }
    }


//    @ApiOperation("批量删除")
//    @PostMapping("/deleteByBatch")
//    public ResultJson deleteByBatch(String ids) {
//        messageService.deleteBatch(ids.split(","));
//        return null;
//    }


}
