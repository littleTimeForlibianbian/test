package com.example.lixc.service;

import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.query.UserQuery;
import com.github.pagehelper.Page;

/**
 * @author lixc
 * @Description
 * @createTime 2020/7/3 10:42
 */
public interface SelectUserService {

    Page<UserBack> selectForList(UserQuery userQuery);

    ResultJson enableUser(UserQuery userQuery);

    ResultJson painterDetail(UserQuery userQuery);

    Page<UserBack> selectForActiveList(UserQuery userQuery);
}
