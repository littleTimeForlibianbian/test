package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.Privilege;
import com.example.lixc.entity.Role;
import com.example.lixc.entity.RolePrivilege;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePrivledgeMapper extends SwBaseMapper<RolePrivilege> {

    int deleteByRoleId(int roleId);

    int deleteByRoleIdBatch(String[] ids);

    //根据权限id 查询对应的角色信息
    List<Role> getRolesByPrivilegeId(@Param("id") int privilegeId);

    //根据角色Id查询对应的权限信息 参数中使用占位符时应该使用${#roleId}
    List<Privilege> getPrivilegesByRoleId(@Param("id") int roleId);
}