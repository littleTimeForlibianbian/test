package com.example.lixc.service.impl;

import com.example.lixc.config.security.utils.SysConfigUtil;
import com.example.lixc.entity.Policy;
import com.example.lixc.mapper.PolicyMapper;
import com.example.lixc.service.PolicyService;
import com.example.lixc.util.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @className: PolicyImpl
 * @description: 平台协议service
 * @Author: Wilson
 * @createTime 2020/7/22 22:24
 */
@Service
@Slf4j
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private PolicyMapper policyMapper;

    @Override
    public ResultJson addPolicy(Policy policy) {
        policyMapper.insertSelective(policy);
        log.info("添加协议成功");
        return ResultJson.buildSuccess("添加协议成功");
    }

    @Override
    public Policy queryPolicy(String type) {
        Policy policy = new Policy();
        policy.setType(type);
        return policyMapper.selectOne(policy);
    }


    @Override
    public ResultJson updatePolicy(Policy policy) {
        if (policy.getId() == null || policy.getId() <= 0) {
            log.error("传入id为空");
            return ResultJson.buildError("传入id为空");
        }
        Policy policy1 = policyMapper.selectByPrimaryKey(policy.getId());
        policy1.setUpdateBy(SysConfigUtil.getLoginUserId());
        policy1.setUpdateTime(new Date());
        policyMapper.updateByPrimaryKeySelective(policy);
        log.info("更新协议成功");
        return ResultJson.buildSuccess("更新协议成功");
    }
}
