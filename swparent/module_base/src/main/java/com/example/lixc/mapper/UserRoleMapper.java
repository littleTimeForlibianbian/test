package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.Role;
import com.example.lixc.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper extends SwBaseMapper<UserRole> {
    List<Role> selectListByUserId(@Param("userId") int userId);

}