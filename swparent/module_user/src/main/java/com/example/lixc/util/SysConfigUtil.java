package com.example.lixc.util;

import com.example.lixc.config.security.entity.JwtUser;
import com.example.lixc.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class SysConfigUtil {


    /**
     * 获取当前登录的用户
     *
     * @return
     */
    public static JwtUser getLoginUser() {
        return (JwtUser) getCurrentUserAuthentication().getPrincipal();
    }


    public static Authentication getCurrentUserAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取档期登录用户的userId
     *
     * @return
     */
    public static int getLoginUserId() {
        JwtUser loginUser = getLoginUser();
        return loginUser.getId();
    }


}
