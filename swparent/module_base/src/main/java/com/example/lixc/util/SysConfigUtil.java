package com.example.lixc.util;

import com.example.lixc.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SysConfigUtil {


    /**
     * 获取当前登录的用户
     *
     * @return
     */
    public static User getLoginUser() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user != null)
            user.setPassword(null);
        return user;
    }

    /**
     * 获取档期登录用户的userId
     *
     * @return
     */
    public static int getLoginUserId() {
        User loginUser = getLoginUser();
        return loginUser.getId();
    }


}
