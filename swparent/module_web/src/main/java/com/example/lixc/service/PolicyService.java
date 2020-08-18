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

    /**
     * 添加使用协议
     *
     * @param policy 协议表对象
     * @return
     */
    ResultJson addPolicy(Policy policy);

    /**
     * 查询使用协议详情
     *
     * @param type 协议类型  use  使用歇息 private  隐私
     * @return
     */
    Policy queryPolicy(String type);

    /**
     * 查询使用协议详情
     *
     * @param policy 协议表对象
     * @return
     */
    ResultJson updatePolicy(Policy policy);
}
