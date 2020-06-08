package com.example.lixc.service;

import com.example.lixc.util.ResultJson;
import com.example.lixc.vo.back.RoleBack;
import com.example.lixc.vo.query.RoleQuery;
import com.github.pagehelper.Page;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/7 22:41
 */
public interface RoleService {

    Page<RoleBack> selectForList(RoleQuery roleQuery);

    ResultJson insert(RoleQuery roleQuery);

    ResultJson detail(RoleQuery roleQuery);

    ResultJson update(RoleQuery roleQuery);

    ResultJson delete(RoleQuery roleQuery);

//    ResultJson deleteBatch(String ids);

    ResultJson getAllPrivileges();

}
