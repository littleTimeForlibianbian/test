package com.example.lixc.service;

import com.example.lixc.util.ResultJson;

import java.util.Map;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/7 11:35
 */
public interface DataService {
    /**
     * 获取新增用户接口
     *
     * @param days      时间段
     * @param isPainter 是否是画师
     * @return
     */
    ResultJson getAddUser(int days, boolean isPainter);

    ResultJson getUserAreaDistributed(boolean isPainter);
}
