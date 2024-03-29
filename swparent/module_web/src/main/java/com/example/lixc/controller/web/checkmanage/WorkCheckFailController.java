package com.example.lixc.controller.web.checkmanage;

import com.example.lixc.service.WorkService;
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
 * @createTime 2020/7/3 17:09
 */
@Api("作品审核失败")
@Slf4j
@RestController
@RequestMapping("/web/manager/check/workFail")
public class WorkCheckFailController {
    @Autowired
    private WorkService workService;

    @ApiOperation("作品审核失败")
    @PostMapping("/workCheckFail")
    public Page<WorkBack> workCheckFail(WorkQuery workQuery) {
        try {
            return workService.workList(workQuery);
        } catch (Exception e) {
            log.error("workList exception:{}", e.getMessage());
            return new Page<>();
        }
    }
}
