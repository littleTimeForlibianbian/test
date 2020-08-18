package com.example.lixc.config.security.service;

import com.example.lixc.config.security.entity.JwtUser;
import com.example.lixc.entity.Privilege;
import com.example.lixc.entity.User;
import com.example.lixc.mapper.UserMapper;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.query.UserQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/14 17:17
 */
@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        UserQuery userQuery = new UserQuery();
        userQuery.setUserName(userName);
        //根据用户名称查询用户信息
        UserBack user = userMapper.selectByUserName(userQuery);
        //根据用户名称查询用户权限
        List<Privilege> list = userMapper.selectPrivilegeListByUserName(userName);
        log.info("userName:{},对应的权限为：{}", userName, list.toString());
        if (!CollectionUtils.isEmpty(list)) {
            user.setPrivilegeList(list);
        }
        return new JwtUser(user);
    }
}
