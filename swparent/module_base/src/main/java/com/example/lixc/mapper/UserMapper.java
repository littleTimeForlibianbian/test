package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.dto.UserInfoDTO;
import com.example.lixc.entity.LoginRecord;
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

    int existsWithPhone(@Param("phone") String phone, @Param("isManager") String isManager);

    int existsWithEmail(@Param("email") String email, @Param("isManager") String isManager);

    int existsWithNickName(@Param("nickName") String nickName, @Param("isManager") String isManager);

    //登录
    UserBack selectByUserName(UserQuery userQuery);

    List<Integer> selectAllNormalUserIds();

    UserBack selectByEmail(UserQuery userQuery);

    //根据id查询用户详情 包含标签和属性
    UserBack selectById(Integer userId);


    List<AdminUserBack> selectAllAdminUsers(AdminUserQuery adminUserQuery);

    int delByIds(String[] ids);

    int delUserRoleRelationByUserId(String[] ids);


    int selectCountByRoleId(int roleId);

    //查询普通用户
    List<UserBack> selectAllUser(UserQuery user);

    //用户基础信息
    User selectBaseByUserName(UserQuery userQuery);

    //缓存用户信息
    List<UserInfoDTO> selectUserInfo();


    //查询被关注的画师id
    List<UserBack> selectFocusPainterIds(UserQuery userQuery);

}
