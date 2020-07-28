package com.example.lixc.service.impl;

import com.example.lixc.entity.Policy;
import com.example.lixc.mapper.PolicyMapper;
import com.example.lixc.service.PolicyService;
import com.example.lixc.util.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: PolicyImpl
 * @description: TODO
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
        return ResultJson.buildSuccess();
    }
}
