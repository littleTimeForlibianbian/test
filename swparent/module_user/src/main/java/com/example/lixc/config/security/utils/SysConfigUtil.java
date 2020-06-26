package com.example.lixc.config.security.utils;

import com.example.lixc.config.security.entity.JwtUser;
import com.example.lixc.entity.User;
import com.example.lixc.mapper.SysConfigMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Resource;

/**
 * 系统配置处理类
 */
@Configuration
public class SysConfigUtil {

    private static SysConfigMapper sysConfigMapper;

    @Autowired
    public void setSysConfigMapper(SysConfigMapper sysConfigMapper) {
        SysConfigUtil.sysConfigMapper = sysConfigMapper;
    }

    //    /**
//     * 获取当前登录的用户
//     *
//     * @return
//     */
//    public static JwtUser getLoginUser() {
//        return (JwtUser) getCurrentUserAuthentication().getPrincipal();
//    }
//
//
//    public static Authentication getCurrentUserAuthentication() {
//        return SecurityContextHolder.getContext().getAuthentication();
//    }
//

    /**
     * 获取档期登录用户的userId
     *
     * @return
     */
    public static int getLoginUserId() {
//        JwtUser loginUser = getLoginUser();
//        return loginUser.getId();
        return 0;
    }

    /**
     * 查询作品上传最大画风标签数量
     *
     * @return
     */
    public static int selectMaxWorkStyleLabelCount() {
        return sysConfigMapper.selectAll().get(0).getMaxWorkStyleLabelCount();
    }

    /**
     * 查询作品上传最大品类标签数量
     *
     * @return
     */
    public static int selectMaxWorkCategoryLabelCount() {
        return sysConfigMapper.selectAll().get(0).getMaxWorkCategoryLabelCount();
    }

}
