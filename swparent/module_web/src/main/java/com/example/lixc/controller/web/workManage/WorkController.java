package com.example.lixc.controller.web.workManage;

import com.example.lixc.constants.SwConstant;
import com.example.lixc.service.WorkService;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.WorkBack;
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
@RequestMapping("/web/admin/check/")
public class WorkController {

    @Autowired
    private WorkService workService;

    @ApiOperation("作品列表")
    @PostMapping("/selectForList")
    //isNormal  是否是普通上传   除了普通上传还有身份认证上传
    //与身份审核的作品列表公用同一个接口
    //作品图  显示第一张作品的缩略图
    public Page<WorkBack> selectForList(WorkQuery workQuery) {
        try {
            workQuery.setIsNormal("N");
            return workService.workList(workQuery, null);
        } catch (Exception e) {
            log.error("获取作品列表异常：{}", e.getMessage());
            return new Page<>();
        }
    }

    @ApiOperation("活跃作品")
    @PostMapping("/active")
    public Page<WorkBack> active(WorkQuery workQuery) {
        try {
            //todo  确定什么样的作品是活跃作品
            workQuery.setPraiseNum(SwConstant.PRAISENUM);
            return workService.workList(workQuery, null);
        } catch (Exception e) {
            log.error("获取活跃作品列表异常：{}", e.getMessage());
            return new Page<>();
        }
    }


    @ApiOperation("关注的画师")
    @PostMapping("/attentionPainter")
    public ResultJson attentionPainter() {
        System.out.println("关注的画师");
        return null;
    }

}
