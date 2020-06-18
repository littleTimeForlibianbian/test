package com.example.lixc.controller.portal;

import com.example.lixc.service.IndexService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.query.SysWorkQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Api("首页管理类")
@RestController
@RequestMapping("/portal/index")
@Slf4j
public class IndexController {

    @Autowired
    private IndexService indexService;

    //查询作品列表
    @ApiOperation("查询作品列表")
    @PostMapping("/workList")
    public ResultJson workList(SysWorkQuery query) {
        //头部  根据是否登录 以及当前用户的是否画师审核成功  判断是否需要显示myWorld

        //列表  查询所有的作品列表
        return ResultJson.buildSuccess();

    }

    //作品详情
    @ApiOperation("作品详情")
    @PostMapping("/workDetail")
    public ResultJson workDetail() {
        return ResultJson.buildSuccess();
    }


    @ApiOperation("上传图片")
    @PostMapping("/uploadImage")
    public ResultJson uploadImage(@RequestParam("file") MultipartFile[] files) {
        return indexService.uploadImage(files);
    }

    //点赞功能

    //推荐给所有关注我的人

    //分享

    //评论

}
