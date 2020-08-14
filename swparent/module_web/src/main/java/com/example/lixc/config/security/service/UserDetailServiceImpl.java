package com.example.lixc.config.security.service;

import com.example.lixc.config.security.entity.JwtUser;
import com.example.lixc.entity.User;
import com.example.lixc.mapper.UserMapper;
import com.example.lixc.vo.query.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/14 17:17
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserQuery user = new UserQuery();
        user.setUserName(s);
        return new JwtUser(userMapper.selectByUserName(user));
    }
}
