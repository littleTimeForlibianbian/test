package com.example.lixc.controller.portal;

import com.example.lixc.entity.SysReport;
import com.example.lixc.entity.WComment;
import com.example.lixc.service.ReportService;
import com.example.lixc.service.SysDictService;
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
import java.util.List;

@Api("首页管理类")
@RestController
@RequestMapping("/portal/index")
@Slf4j
public class IndexController {

    @Autowired
    private WorkService workService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private SysDictService dictService;

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
    @GetMapping("/workDetail")
    public ResultJson workDetail(Integer workId) {
        try {
            return workService.workDetail(workId);
        } catch (Exception e) {
            log.error("workList exception:{}", e.getMessage());
            return ResultJson.buildError("获取作品详情发生异常");
        }
    }

    //作品详情
    @ApiOperation("作品对应评论列表")
    @PostMapping("/getWorkComment")
    public ResultJson getWorkComment(Integer workId) {
        try {
            return workService.getWorkComment(workId);
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


    //上传图片 file
    @ApiOperation("上传图片")
    @PostMapping("/uploadImage")
    public ResultJson uploadImage(@RequestParam("file") MultipartFile file) {
        MultipartFile[] files = new MultipartFile[]{file};
        return workService.uploadImage(files);
    }

    @ApiOperation("上传图片")
    @PostMapping("/uploadImageBase64")
    public ResultJson uploadImageBase64(String picString) {
//        return workService.uploadImageBase64(picString);
        return workService.uploadImageToServer(picString);
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
    public ResultJson uploadWork(@RequestBody WorkQuery workQuery) {
        try {
            return workService.uploadWork(workQuery);
        } catch (Exception e) {
            log.error("上传作品异常：{}", e.getMessage());
            return ResultJson.buildError("上传作品异常");
        }

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

    //关注  对人进行关注
    @ApiOperation("取消关注")
    @PostMapping("/cancelFocus")
    public ResultJson cancelFocus(String toUserId) {
        try {
            return workService.cancelFocus(toUserId);
        } catch (Exception e) {
            log.error("addWebsite exception:{}", e.getMessage());
            return ResultJson.buildError("添加常用网站发生异常");
        }
    }

    //点赞功能 对作品点赞
    @ApiOperation("点赞")
    @PostMapping("/like")
    public ResultJson like(String workId, Integer fromUserId) {
        try {
            return workService.like(workId, fromUserId);
        } catch (Exception e) {
            log.error("addWebsite exception:{}", e.getMessage());
            return ResultJson.buildError("添加常用网站发生异常");
        }
    }

    //推荐给所有关注我的人
    @ApiOperation("推荐")
    @PostMapping("/recommend")
    public ResultJson recommend(String workId) {
        try {
            return workService.recommend(workId);
        } catch (Exception e) {
            log.error("addWebsite exception:{}", e.getMessage());
            return ResultJson.buildError("推荐异常");
        }
    }

    //分享
    @ApiOperation("分享")
    @PostMapping("/share")
    public ResultJson share(HttpServletRequest request, String workId) {
        try {
            StringBuffer requestURL = request.getRequestURL();
            String text = requestURL.toString().substring(0, requestURL.lastIndexOf("/") - 12) + "logon?forwardParam=workDetail-" + workId;
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
            return ResultJson.buildError("评论异常");
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
    public ResultJson report(String ids) {
        try {
            return workService.report(ids);
        } catch (Exception e) {
            log.error("举报失败:{}", e.getMessage());
            return ResultJson.buildError("举报失败");
        }
    }

    //举报
    @ApiOperation("举报选项集合")
    @PostMapping("/reportList")
    public ResultJson reportList() {
        SysReport report = new SysReport();
        List<SysReport> sysReports = reportService.selectForList(report);
        return ResultJson.buildSuccess(sysReports);
    }

    /**
     * 获取标签
     *
     * @param type category==品类 其余代表画风
     * @return
     */
    @ApiOperation("获取标签集合")
    @PostMapping("/getDict")
    public ResultJson getDict(String type) {
        try {
            return ResultJson.buildSuccess(dictService.getDict(type));
        } catch (Exception e) {
            log.error("获取作品标签失败:{}", e.getMessage());
        }
        return ResultJson.buildError("获取标签失败");
    }
}
