package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.RolePrivilege;

public interface RolePrivledgeMapper extends SwBaseMapper<RolePrivilege> {

    int deleteByRoleId(int roleId);
}