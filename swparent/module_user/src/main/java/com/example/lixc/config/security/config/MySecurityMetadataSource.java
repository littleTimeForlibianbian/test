package com.example.lixc.config.security.config;

import com.example.lixc.entity.Privilege;
import com.example.lixc.entity.Role;
import com.example.lixc.mapper.PrivilegeMapper;
import com.example.lixc.vo.back.PrivilegeBack;
import org.apache.shiro.util.AntPathMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/14 22:31
 */
@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {


    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取请求的url
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        System.out.println(requestUrl);
        //查询所有的权限
        List<PrivilegeBack> list = privilegeMapper.findAllPrivilege();
        for (PrivilegeBack p : list) {
            //TODO  调整sql编写
            if (antPathMatcher.match(p.getUrl(), requestUrl) && p.getRoles().size() > 0) {
                int size = p.getRoles().size();
                //定义一个数组，来接收能访问该资源的角色
                String[] roleNameArray = new String[size];
                for (int i = 0; i < size; i++) {
                    roleNameArray[i] = p.getRoles().get(i).getName();
                }
                return SecurityConfig.createList(roleNameArray);
            }
        }
        //如果遍历完menu之后没有匹配上，说名访问该资源不需要权限信息，设置一个登陆就能访问的角色
        return SecurityConfig.createList("ROLE_login");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
