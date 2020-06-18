package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.Role;
import com.example.lixc.entity.RolePrivilege;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePrivledgeMapper extends SwBaseMapper<RolePrivilege> {

    int deleteByRoleId(int roleId);

    //根据权限id 查询对应的角色信息
    List<Role> getRolesByPrivilegeId(@Param("id") int privilegeId);
}