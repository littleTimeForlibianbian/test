package com.example.lixc.service;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/1 14:52
 */
public interface ICodeService {

    /**
     * 生成邀请码
     *
     * @return 邀请码
     */
    String genInvitationCode();


    /**
     * 查询邀请码是否存在
     * @param code 邀请码
     * @return
     */
    int selectCountByCode(String code);
}
