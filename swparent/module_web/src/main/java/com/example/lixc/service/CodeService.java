package com.example.lixc.service;

import com.example.lixc.util.ResultJson;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/1 14:52
 */
public interface CodeService {

    /**
     * 生成邀请码
     *
     * @return 邀请码
     */
    ResultJson genInvitationCode();


    /**
     * 查询邀请码是否存在
     *
     * @param code 邀请码
     * @return
     */
    int selectCountByCode(String code);


}
