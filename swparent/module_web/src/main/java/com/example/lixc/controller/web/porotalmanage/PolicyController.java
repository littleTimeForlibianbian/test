package com.example.lixc.controller.web.porotalmanage;

import com.example.lixc.config.security.utils.SysConfigUtil;
import com.example.lixc.entity.Policy;
import com.example.lixc.enums.PolicyTypeEnum;
import com.example.lixc.service.PolicyService;
import com.example.lixc.util.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/27 12:10
 */
@Api("协议管理")
@Slf4j
@RestController
@RequestMapping("/web/manager/policy")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @ApiOperation("查询协议")
    @PostMapping("/queryPolicy")
    public ResultJson queryPolicy(String type) {
        try {
            return ResultJson.buildSuccess(policyService.queryPolicy(type));
        } catch (Exception e) {
            log.error("查询协议异常:{}", e.getMessage());
            return ResultJson.buildError("查询协议异常");
        }
    }


    @ApiOperation("添加使用协议")
    @PostMapping("/updatePolicy")
    public ResultJson updatePolicy(Policy policy) {
        try {
            Policy policyDb = policyService.queryPolicy(policy.getType());
            if (policyDb == null) {
                //如果不存在记录  插入
                policy.setCreateTime(new Date());
                policy.setCreateBy(SysConfigUtil.getLoginUserId());
                policyService.addPolicy(policy);
            } else {
                //如果存在 更新
                policyDb.setUpdateTime(new Date());
                policyDb.setUpdateBy(SysConfigUtil.getLoginUserId());
                policyDb.setContent(policy.getContent());
                policyService.updatePolicy(policyDb);
            }
            return ResultJson.buildSuccess("添加成功");
        } catch (Exception e) {
            log.error("添加使用协议异常:{}", e.getMessage());
            return ResultJson.buildError("添加使用协议异常");
        }
    }
}
