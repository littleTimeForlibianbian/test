package com.example.lixc.mapper;

import com.example.lixc.basemapper.SwBaseMapper;
import com.example.lixc.entity.User;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/1 13:40
 */
public interface UserMapper extends SwBaseMapper<User> {

    int existsWithPhone(String phone);

    int existsWithEmail(String email);

    User selectByUserName(User user);
}
