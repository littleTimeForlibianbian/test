package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.LoginRecord;
import com.example.lixc.entity.User;
import com.example.lixc.vo.back.AdminUserBack;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.query.AdminUserQuery;
import com.example.lixc.vo.query.UserQuery;

import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/1 13:40
 */
public interface UserMapper extends SwBaseMapper<User> {

    int existsWithPhone(String phone, String isManager);

    int existsWithEmail(String email, String isManager);

    //登录
    UserBack selectByUserName(UserQuery user);


    List<AdminUserBack> selectAllAdminUsers(AdminUserQuery adminUserQuery);

    int delByIds(String[] ids);

    int selectCountByRoleId(int roleId);

    //查询普通用户
    List<UserBack> selectAllUser(UserQuery user);

}
