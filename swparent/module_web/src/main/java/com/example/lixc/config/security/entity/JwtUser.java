package com.example.lixc.config.security.entity;

import com.example.lixc.entity.Privilege;
import com.example.lixc.entity.Role;
import com.example.lixc.entity.User;
import com.example.lixc.vo.back.UserBack;
import com.example.lixc.vo.query.UserQuery;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * @author lixc
 * @Description jwtUser实体类
 * @createTime 2020/6/14 17:04
 */
@Data
public class JwtUser implements UserDetails {

    private static final long serialVersionUID = 433122213302060925L;
    private Integer id;
    private String username;
    private String password;
    private UserBack userBack;
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * 写一个能直接使用user创建jwtUser的构造器GrantedAuthority
     *
     * @param user
     */
    public JwtUser(UserBack user) {
        id = user.getId();
        username = user.getNickName();
        password = user.getPassword();
        userBack = user;
        Set<SimpleGrantedAuthority> singleton = new HashSet<>(Collections.singleton(new SimpleGrantedAuthority("default")));
        singleton.remove(singleton.stream().findFirst().get());
        //去掉默认的，重新从角色中添加
        for (Privilege p : user.getPrivilegeList()) {
            singleton.add(new SimpleGrantedAuthority(p.getTag()));
        }
        authorities = singleton;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}   
