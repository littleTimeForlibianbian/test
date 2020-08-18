package com.example.lixc.controller.web.workmanage;

import com.example.lixc.constants.SwConstant;
import com.example.lixc.service.IdenCheckService;
import com.example.lixc.service.WorkService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.back.WorkBack;
import com.example.lixc.vo.query.UserQuery;
import com.example.lixc.vo.query.WorkQuery;
import com.github.pagehelper.Page;
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
 * @createTime 2020/6/27 10:19
 */
@Api("作品管理")
@Slf4j
@RestController
@RequestMapping("/web/manager/work")
public class WorkController {

    @Autowired
    private WorkService workService;

    @Autowired
    private IdenCheckService idenCheckService;

    @ApiOperation("作品列表")
    @PostMapping("/selectForList")
    //isNormal  是否是普通上传   除了普通上传还有身份认证上传
    //与身份审核的作品列表公用同一个接口
    //作品图  显示第一张作品的缩略图
    public Page<WorkBack> selectForList(WorkQuery workQuery) {
        try {
            workQuery.setIsNormal("Y");
            return workService.workList(workQuery);
        } catch (Exception e) {
            log.error("获取作品列表异常：{}", e.getMessage());
            return new Page<>();
        }
    }

    @ApiOperation("作品详情")
    @PostMapping("/detail")
    public ResultJson detail(Integer workId) {
        try {
            return workService.workDetail(workId);
        } catch (Exception e) {
            log.error("【作品管理】获取作品详情异常：{}", e.getMessage());
            return ResultJson.buildError("【作品管理】获取作品详情异常");
        }
    }


    @ApiOperation("删除作品")
    @PostMapping("/delete")
    public ResultJson delete(Integer workId) {
        try {
            return workService.workDel(workId);
        } catch (Exception e) {
            log.error("【作品管理】获取作品详情异常：{}", e.getMessage());
            return ResultJson.buildError("【作品管理】获取作品详情异常");
        }
    }


    @ApiOperation("活跃作品")
    @PostMapping("/active")
    public Page<WorkBack> active(WorkQuery workQuery) {
        try {
            workQuery.setPraiseNum(SwConstant.PRAISENUM);
            return workService.workList(workQuery);
        } catch (Exception e) {
            log.error("获取活跃作品列表异常：{}", e.getMessage());
            return new Page<>();
        }
    }


    @ApiOperation("关注的画师")
    @PostMapping("/attentionPainter")
    public Page<UserBack> attentionPainter(UserQuery userQuery) {
        Page<UserBack> userBacks = null;
        try {
            userBacks = idenCheckService.focusPainterList(userQuery);
        } catch (Exception e) {
            log.error("查询关注的画师异常:{}", e.getMessage());
            userBacks = new Page<>();
        }
        return userBacks;
    }

}
