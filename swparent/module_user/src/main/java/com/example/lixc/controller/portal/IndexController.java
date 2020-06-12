package com.example.lixc.controller.portal;

import com.example.lixc.util.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("首页管理类")
@RestController
@RequestMapping("/portal/user")
@Slf4j
public class IndexController {

    //查询作品列表
    @ApiOperation("查询作品列表")
    @PostMapping("/workList")
    public ResultJson workList() {
        //头部  根据是否登录 判断是否需要显示myWorld


        //列表  查询所有的作品列表
        return ResultJson.buildSuccess();

    }

    //作品详情
    @ApiOperation("作品详情")
    @PostMapping("/workDetail")
    public ResultJson workDetail() {
        return ResultJson.buildSuccess();
    }


    /**
     *  登录以后才会有以下功能
     */

    //点赞功能

    //推荐给所有关注我的人

    //分享

    //评论

    //编辑

}
