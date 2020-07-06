package com.example.lixc.controller.web.checkManage;

import com.example.lixc.common.PageData;
import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.WorkBack;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/3 17:07
 */
@Api("作品审核")
@Slf4j
@RestController
@RequestMapping("/web/admin/check/work")
public class WorkCheckController {

    @ApiOperation("作品审核")
    @PostMapping("/workCheck")
    public ResultJson workCheck() {
        System.out.println("workCheck");
        return null;
    }

    @ApiOperation("作品审核列表")
    @PostMapping("/list")
    public Page<WorkBack> list() {
        try {
            return indexService.workList(query, more);
        } catch (Exception e) {
            log.error("workList exception:{}", e.getMessage());
            return new Page<>();
        }
    }


}
