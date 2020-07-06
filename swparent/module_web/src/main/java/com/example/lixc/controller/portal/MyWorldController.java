package com.example.lixc.controller.portal;

import com.example.lixc.service.MyWorldService;
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
 * @createTime 2020/6/27 11:15
 */
@Api("我的世界")
@RestController
@RequestMapping("/portal/myWorld")
@Slf4j
public class MyWorldController {

    @Autowired
    private MyWorldService service;

    @ApiOperation("最新消息")
    @PostMapping("/newMessage")
    public ResultJson newMessage() {
        try {
            return service.newMessage();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResultJson.buildError("查询最新消息异常");
    }

    @ApiOperation("最新消息已读")
    @PostMapping("/newMessage")
    public ResultJson newMessageRead(int messageId) {
        try {
            return service.newMessageRead(messageId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResultJson.buildError("查询最新消息已读异常");
    }

    @ApiOperation("查询数量")
    @PostMapping("/queryCount")
    public ResultJson queryCount() {
        try {
            return service.queryCount();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResultJson.buildError("查询数量异常");
    }


    @ApiOperation("喜欢")
    @PostMapping("/myLike")
    public ResultJson myLike() {
        try {
            return service.myLike();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResultJson.buildError("查询我喜欢异常");
    }

    @ApiOperation("关注")
    @PostMapping("/myFocus")
    public ResultJson myFocus() {
        try {
            return service.myFocus();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResultJson.buildError("查询最新消息已读异常");
    }

    @ApiOperation("动态消息")
    @PostMapping("/news")
    public ResultJson news() {
        try {
            return service.news();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResultJson.buildError("查询最新消息已读异常");
    }


    @ApiOperation("系统消息")
    @PostMapping("/systemMessage")
    public ResultJson systemMessage() {
        try {
            //查询消息类型为announcement公告的，公告为后台管理员发送，为系统消息
            return service.systemMessage();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResultJson.buildError("查询最新消息已读异常");
    }

    @ApiOperation("添加建议反馈")
    @PostMapping("/addFeedBack")
    public ResultJson addFeedBack(String content) {
        try {
            return service.addFeedBack(content);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResultJson.buildError("查询最新消息已读异常");
    }

    @ApiOperation("点赞建议反馈")
    @PostMapping("/feedBackLike")
    public ResultJson feedBackLike(String id) {
        try {
            return service.feedBackLike(id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResultJson.buildError("点赞建议反馈");
    }

    @ApiOperation("查询建议反馈")
    @PostMapping("/feedBackLike")
    public ResultJson feedBackList() {
        try {
            return service.feedBackList();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResultJson.buildError("查询建议反馈异常");
    }


    @ApiOperation("反馈优化")
    @PostMapping("/feedBackOpt")
    public ResultJson feedBackOpt() {
        try {
            return service.feedBackOpt();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResultJson.buildError("查询最新消息已读异常");
    }

    @ApiOperation("版本剧透")
    @PostMapping("/versionSpoiler")
    public ResultJson versionSpoiler() {
        try {
            return service.versionSpoiler();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResultJson.buildError("查询最新消息已读异常");
    }


    @ApiOperation("邀请好友")
    @PostMapping("/inviteFriends")
    public ResultJson inviteFriends() {
        //生成邀请码 以及获取当前用户的信息
        return null;
    }


    @ApiOperation("全部消息")
    @PostMapping("/allMessage")
    public ResultJson allMessage() {
        try {
            return service.allMessage();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResultJson.buildError("查询最新消息已读异常");
    }


}
