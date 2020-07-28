package com.example.lixc.controller.web.porotalManage;

import com.example.lixc.config.security.utils.SysConfigUtil;
import com.example.lixc.entity.Policy;
import com.example.lixc.enums.PolicyTypeEnum;
import com.example.lixc.service.PolicyService;
import com.example.lixc.util.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
@RequestMapping("/web/admin/policy")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @ApiOperation("添加使用协议")
    @PostMapping("/addUsePolicy")
    public ResultJson addUsePolicy(String content) {
        try {
            Policy policy = new Policy();
            policy.setContent(content);
            policy.setType(PolicyTypeEnum.POLICY_TYPE_USE.getCode());
            policy.setCreateBy(SysConfigUtil.getLoginUserId());
            policy.setCreateTime(new Date());
            return policyService.addPolicy(policy);
        } catch (Exception e) {
            log.error("添加使用协议异常:{}", e.getMessage());
            return ResultJson.buildError("添加使用协议异常");
        }
    }


    @ApiOperation("添加隐私协议")
    @PostMapping("/addPrivatePolicy")
    public ResultJson addPrivatePolicy(String content) {
        try {
            Policy policy = new Policy();
            policy.setContent(content);
            policy.setType(PolicyTypeEnum.POLICY_TYPE_PRIVATE.getCode());
            policy.setCreateBy(SysConfigUtil.getLoginUserId());
            policy.setCreateTime(new Date());
            return policyService.addPolicy(policy);
        } catch (Exception e) {
            log.error("添加隐私协议异常:{}", e.getMessage());
            return ResultJson.buildError("添加隐私协议异常");
        }

    }
}
