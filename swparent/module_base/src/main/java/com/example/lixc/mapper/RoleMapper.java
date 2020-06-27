package com.example.lixc.mapper;


import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.Role;
import com.example.lixc.entity.User;
import com.example.lixc.vo.back.RoleBack;
import com.example.lixc.vo.query.RoleQuery;

import java.util.List;

public interface RoleMapper extends SwBaseMapper<Role> {
    List<RoleBack> selectForList(RoleQuery roleQuery);
//    int deleteBatch(String[] ids);
}