package com.example.lixc.controller.portal;

import com.example.lixc.service.IndexService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.query.WorkQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api("首页管理类")
@RestController
@RequestMapping("/portal/index")
@Slf4j
public class IndexController {

    @Autowired
    private IndexService indexService;

    //查询作品列表
    @ApiOperation("查询作品列表")
    @PostMapping("/workList/")
    public ResultJson workList(@RequestBody WorkQuery query, @RequestParam("more") boolean more) {
        try {
            return indexService.workList(query, more);
        } catch (Exception e) {
            log.error("workList exception:{}", e.getMessage());
            return ResultJson.buildError("获取作品标签发生异常");
        }
    }


    //作品详情
    @ApiOperation("作品详情")
    @PostMapping("/workDetail")
    public ResultJson workDetail(WorkQuery query) {
        try {
            return indexService.workDetail(query);
        } catch (Exception e) {
            log.error("workList exception:{}", e.getMessage());
            return ResultJson.buildError("获取作品标签发生异常");
        }
    }


    //上传图片
    @ApiOperation("上传图片")
    @PostMapping("/uploadImage")
    public ResultJson uploadImage(@RequestParam("file") MultipartFile[] files) {
        return indexService.uploadImage(files);
    }

    @ApiOperation("获取作品标签")
    @PostMapping("/selectAllWorkLabels")
    public ResultJson selectAllWorkLabels() {
        try {
            return indexService.selectAllWorkLabels();
        } catch (Exception e) {
            log.error("selectAllWorkLabels exception:{}", e.getMessage());
            return ResultJson.buildError("获取作品标签发生异常");
        }
    }


    @ApiOperation("上传作品")
    @PostMapping("/uploadWork")
    public ResultJson uploadWork(WorkQuery workQuery) {
        return indexService.uploadWork(workQuery);
    }


    //点赞功能
    @ApiOperation("关注")
    @PostMapping("/focus")
    public ResultJson focus(String toUserId) {
        //插入到我喜欢的作品表
        //更新该作品的
        return null;
    }


    //点赞功能
    @ApiOperation("点赞")
    @PostMapping("/like")
    public ResultJson like(WorkQuery workQuery) {
        //插入到我喜欢的作品表
        //更新作品的点赞数量
        return null;
    }

    //推荐给所有关注我的人
    @ApiOperation("推荐")
    @PostMapping("/recommend")
    public ResultJson recommend() {
        return null;
    }

    //分享 ---生成一个分享的二维码就ok
    @ApiOperation("分享")
    @PostMapping("/share")
    public ResultJson share() {
        return null;
    }

    //评论
    @ApiOperation("评论")
    @PostMapping("/comment")
    public ResultJson comment() {
        return null;
    }

    //举报
    @ApiOperation("举报")
    @PostMapping("/report")
    public ResultJson report() {
        return null;
    }

}
