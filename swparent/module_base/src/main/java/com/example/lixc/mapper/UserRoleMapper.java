package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.Role;
import com.example.lixc.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 11930
 */
public interface UserRoleMapper extends SwBaseMapper<UserRole> {
    /**
     * 根据用户id查询对应的角色
     * @param userId
     * @return
     */
    List<Role> selectListByUserId(@Param("userId") int userId);

    /**
     * 根据用户id和角色类型查询对应的角色信息
     * @param userId  用户id
     * @param type 角色类型 1：前台用户角色 2：后台角色
     * @return
     */
    List<Role> selectListByUserIdAndType(@Param("userId") int userId,
                                         @Param("type") int type);

}