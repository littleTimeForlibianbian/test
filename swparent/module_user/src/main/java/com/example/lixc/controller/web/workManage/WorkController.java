package com.example.lixc.controller.web.workManage;

import com.example.lixc.util.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/27 10:19
 */
@Api("作品管理")
@RestController
@RequestMapping("/web/admin/check/")
public class WorkController {

    @ApiOperation("作品列表")
    @PostMapping("/selectForList")
    //isNormal  是否是普通上传   除了普通上传还有身份认证上传
    //与身份审核的作品列表公用同一个接口
    //作品图  显示第一张作品的缩略图
    public ResultJson selectForList(String isNormal) {
        System.out.println("作品列表");
        return null;
    }

    @ApiOperation("活跃作品")
    @PostMapping("/active")
    public ResultJson active() {
        System.out.println("活跃作品");
        return null;
    }


    @ApiOperation("关注的画师")
    @PostMapping("/attentionPainter")
    public ResultJson attentionPainter() {
        System.out.println("关注的画师");
        return null;
    }

}
