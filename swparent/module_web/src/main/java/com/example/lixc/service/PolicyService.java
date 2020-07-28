package com.example.lixc.service;

import com.example.lixc.entity.Policy;
import com.example.lixc.util.ResultJson;

/**
 * @className: PolicyService
 * @description: TODO
 * @Author: Wilson
 * @createTime 2020/7/22 22:22
 */
public interface PolicyService {

    ResultJson addPolicy(Policy policy);
}
