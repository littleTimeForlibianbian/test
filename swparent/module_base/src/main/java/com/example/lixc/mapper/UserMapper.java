package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.User;
import com.example.lixc.vo.back.AdminUserBack;
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
    int selectByUserName(UserQuery user);


    List<AdminUserBack> selectAllAdminUsers(AdminUserQuery adminUserQuery);

    int delByIds(String[] ids);
}
