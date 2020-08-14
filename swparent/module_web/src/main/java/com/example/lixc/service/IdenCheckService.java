package com.example.lixc.service;

import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.query.UserQuery;
import com.github.pagehelper.Page;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/5 14:52
 */
public interface IdenCheckService {
    Page<UserBack> list(UserQuery userQuery);

    ResultJson identifyCheck(UserQuery userQuery);

    Page<UserBack> focusPainterList(UserQuery userQuery);
}
