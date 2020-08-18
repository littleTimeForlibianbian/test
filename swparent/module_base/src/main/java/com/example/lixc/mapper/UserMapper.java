package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.dto.UserInfoDTO;
import com.example.lixc.entity.LoginRecord;
import com.example.lixc.entity.Privilege;
import com.example.lixc.entity.User;
import com.example.lixc.vo.back.AdminUserBack;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.query.AdminUserQuery;
import com.example.lixc.vo.query.UserQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/1 13:40
 */
public interface UserMapper extends SwBaseMapper<User> {

    /**
     * 电话是否存在
     *
     * @param phone
     * @param isManager
     * @return
     */
    int existsWithPhone(@Param("phone") String phone, @Param("isManager") String isManager);

    /**
     * 邮箱是否存在
     *
     * @param email
     * @param isManager
     * @return
     */
    int existsWithEmail(@Param("email") String email, @Param("isManager") String isManager);

    /**
     * 昵称是否存在
     *
     * @param nickName
     * @param isManager
     * @return
     */
    int existsWithNickName(@Param("nickName") String nickName, @Param("isManager") String isManager);

    /**
     * 根据用户名称查询用户信息
     *
     * @param userQuery
     * @return
     */
    UserBack selectByUserName(UserQuery userQuery);

    /**
     * 查询所有的普通用户id
     *
     * @return
     */
    List<Integer> selectAllNormalUserIds();

    /**
     * 根据邮箱查询用户信息
     *
     * @param userQuery
     * @return
     */
    UserBack selectByEmail(UserQuery userQuery);

    /**
     * 据id查询用户详情 包含标签和属性
     *
     * @param userId
     * @return
     */
    UserBack selectById(Integer userId);


    /**
     * 查询所有的管理员用户
     *
     * @param adminUserQuery
     * @return
     */
    List<AdminUserBack> selectAllAdminUsers(AdminUserQuery adminUserQuery);

    /**
     * 批量删除用户
     *
     * @param ids
     * @return
     */
    int delByIds(String[] ids);

    /**
     * 删除用户角色的关联关系
     *
     * @param ids
     * @return
     */
    int delUserRoleRelationByUserId(String[] ids);


    /**
     * 根据角色id查询用户角色关联数量
     *
     * @param roleId 角色Id
     * @return
     */
    int selectCountByRoleId(int roleId);

    /**
     * 查询普通用户
     *
     * @param user
     * @return
     */
    List<UserBack> selectAllUser(UserQuery user);

    /**
     * 用户基础信息
     *
     * @param userQuery
     * @return
     */
    User selectBaseByUserName(UserQuery userQuery);

    /**
     * 缓存用户信息
     *
     * @return
     */
    List<UserInfoDTO> selectUserInfo();


    /**
     * 查询被关注的画师id
     *
     * @param userQuery
     * @return
     */
    List<UserBack> selectFocusPainterIds(UserQuery userQuery);


    List<Privilege> selectPrivilegeListByUserName(@Param("userName") String userName);

}
