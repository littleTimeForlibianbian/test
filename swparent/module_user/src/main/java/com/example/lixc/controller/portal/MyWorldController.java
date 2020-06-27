package com.example.lixc.controller.portal;

import com.example.lixc.util.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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

    @ApiOperation("最新消息")
    @PostMapping("/newMessage")
    public ResultJson newMessage() {
        return null;
    }

    @ApiOperation("最新消息已读")
    @PostMapping("/newMessage")
    public ResultJson newMessageRead() {
        return null;
    }

    @ApiOperation("喜欢")
    @PostMapping("/myLike")
    public ResultJson myLike() {
        return null;
    }

    @ApiOperation("关注")
    @PostMapping("/myFocus")
    public ResultJson myFocus() {
        return null;
    }

    @ApiOperation("动态消息")
    @PostMapping("/news")
    public ResultJson news() {
        return null;
    }


    @ApiOperation("系统消息")
    @PostMapping("/systemMessage")
    public ResultJson systemMessage() {
        return null;
    }

    @ApiOperation("添加建议反馈")
    @PostMapping("/addFeedBHack")
    public ResultJson addFeedBHack() {
        return null;
    }

    @ApiOperation("反馈优化")
    @PostMapping("/feedBackOpt")
    public ResultJson feedBackOpt() {
        return null;
    }

    @ApiOperation("版本剧透")
    @PostMapping("/versionSpoiler")
    public ResultJson versionSpoiler() {
        return null;
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
        return null;
    }


}
