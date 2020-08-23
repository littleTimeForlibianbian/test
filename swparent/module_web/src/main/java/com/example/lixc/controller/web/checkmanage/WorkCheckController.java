package com.example.lixc.controller.web.checkmanage;

import com.example.lixc.enums.WorkStatusEnum;
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

import javax.servlet.http.HttpServletRequest;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/3 17:07
 */
@Api("作品审核")
@Slf4j
@RestController
@RequestMapping("/web/manager/check/work")
public class WorkCheckController {

    @Autowired
    private WorkService workService;

    @ApiOperation("作品审核")
    @PostMapping("/workCheck")
    public ResultJson workCheck(WorkQuery workQuery, HttpServletRequest request) {
        try {
            return workService.workCheck(workQuery,request);
        } catch (Exception e) {
            log.error("workList exception:{}", e.getMessage());
        }
        return ResultJson.buildError("作品审核失败");
    }

    @ApiOperation("作品审核列表")
    @PostMapping("/list")
    public Page<WorkBack> list() {
        try {
            WorkQuery workQuery = new WorkQuery();
            //查询所有待审核的作品
            workQuery.setStatus(WorkStatusEnum.WORK_STATUS_WAIT.getCode());
            return workService.workList(workQuery);
        } catch (Exception e) {
            log.error("workList exception:{}", e.getMessage());
            return new Page<>();
        }
    }


}
