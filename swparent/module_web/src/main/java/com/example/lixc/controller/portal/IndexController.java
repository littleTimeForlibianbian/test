package com.example.lixc.controller.portal;

import com.example.lixc.entity.WComment;
import com.example.lixc.service.WorkService;
import com.example.lixc.util.QRCodeUtil;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.WorkBack;
import com.example.lixc.vo.query.WCommentQuery;
import com.example.lixc.vo.query.WorkQuery;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Api("首页管理类")
@RestController
@RequestMapping("/portal/index")
@Slf4j
public class IndexController {

    @Autowired
    private WorkService workService;

    //查询作品列表  按照时间进行倒叙排序
    @ApiOperation("查询作品列表")
    @PostMapping("/workList/{more}")
    public Page<WorkBack> workList(WorkQuery query, @PathVariable("more") String more) {
        try {
            return workService.workList(query, more);
        } catch (Exception e) {
            log.error("workList exception:{}", e.getMessage());
            return new Page<>();
        }
    }


    //作品详情
    @ApiOperation("作品详情")
    @PostMapping("/workDetail")
    public ResultJson workDetail(WorkQuery query) {
        try {
            return workService.workDetail(query);
        } catch (Exception e) {
            log.error("workList exception:{}", e.getMessage());
            return ResultJson.buildError("获取作品详情发生异常");
        }
    }

    //作品详情
    @ApiOperation("其余作品")
    @PostMapping("/other")
    public ResultJson other(WorkQuery query) {
        try {
            return workService.other(query);
        } catch (Exception e) {
            log.error("workList exception:{}", e.getMessage());
            return ResultJson.buildError("获取作品详情发生异常");
        }
    }


    //上传图片
    @ApiOperation("上传图片")
    @PostMapping("/uploadImage")
    public ResultJson uploadImage(@RequestParam("file") MultipartFile file) {
        MultipartFile[] files = new MultipartFile[]{file};
        return workService.uploadImage(files);
    }

    @ApiOperation("获取作品标签")
    @PostMapping("/selectAllWorkLabels")
    public ResultJson selectAllWorkLabels() {
        try {
            return workService.selectAllWorkLabels();
        } catch (Exception e) {
            log.error("selectAllWorkLabels exception:{}", e.getMessage());
            return ResultJson.buildError("获取作品标签发生异常");
        }
    }


    @ApiOperation("上传作品")
    @PostMapping("/uploadWork")
    public ResultJson uploadWork(WorkQuery workQuery) {
        return workService.uploadWork(workQuery);
    }

    @ApiOperation("创作过往")
    @PostMapping("/createHistory")
    public ResultJson createHistory(String content) {
        return workService.createHistory(content);
    }

    @ApiOperation("常用网站")
    @PostMapping("/addWebsite")
    public ResultJson addWebsite(String website) {
        try {
            return workService.addWebsite(website);
        } catch (Exception e) {
            log.error("addWebsite exception:{}", e.getMessage());
            return ResultJson.buildError("添加常用网站发生异常");
        }
    }


    //关注  对人进行关注
    @ApiOperation("关注")
    @PostMapping("/focus")
    public ResultJson focus(String toUserId) {
        try {
            return workService.focus(toUserId);
        } catch (Exception e) {
            log.error("addWebsite exception:{}", e.getMessage());
            return ResultJson.buildError("添加常用网站发生异常");
        }
    }


    //点赞功能 对作品点赞
    @ApiOperation("点赞")
    @PostMapping("/like")
    public ResultJson like(String workId) {
        try {
            return workService.like(workId);
        } catch (Exception e) {
            log.error("addWebsite exception:{}", e.getMessage());
            return ResultJson.buildError("添加常用网站发生异常");
        }
    }

    //推荐给所有关注我的人
    //TODO 向所有关注我的人发送一条消息
    @ApiOperation("推荐")
    @PostMapping("/recommend")
    public ResultJson recommend() {
        return null;
    }

    //分享 ---生成一个分享的二维码就ok
    //TODO 用户点击或者扫描生成的二维码 首先会跳转到登录页面，登录以后直接跳转到作品详情页
    @ApiOperation("分享")
    @PostMapping("/share")
    public ResultJson share(HttpServletRequest request) {
        try {
            StringBuffer requestURL = request.getRequestURL();
            String text = requestURL.toString().substring(0, requestURL.lastIndexOf("/")) + "workDetail";
            byte[] qrCodeImage = QRCodeUtil.getQRCodeImage(text, 300, 300);
            return ResultJson.buildSuccess(qrCodeImage);
        } catch (Exception e) {
            return ResultJson.buildError("生成二维码异常");
        }
    }

    //评论
    @ApiOperation("评论")
    @PostMapping("/comment")
    public ResultJson comment(WCommentQuery commentQuery) {
        try {
            return workService.comment(commentQuery);
        } catch (Exception e) {
            log.error("addWebsite exception:{}", e.getMessage());
            return ResultJson.buildError("添加常用网站发生异常");
        }
    }

    @ApiOperation("评论点赞")
    @PostMapping("/commentLike")
    public ResultJson commentLike(int id) {
        try {
            return workService.commentLike(id);
        } catch (Exception e) {
            log.error("addWebsite exception:{}", e.getMessage());
            return ResultJson.buildError("添加常用网站发生异常");
        }
    }

    @ApiOperation("评论删除")
    @PostMapping("/commentDel")
    public ResultJson commentDel(int id) {
        try {
            return workService.commentDel(id);
        } catch (Exception e) {
            log.error("addWebsite exception:{}", e.getMessage());
            return ResultJson.buildError("添加常用网站发生异常");
        }
    }


    //举报
    @ApiOperation("举报")
    @PostMapping("/report")
    public ResultJson report() {
        return null;
    }

}
