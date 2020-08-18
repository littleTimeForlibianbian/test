package com.example.lixc.config.security.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author lixc
 * @Description 认证管理器
 * @createTime 2020/6/14 22:54
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute configAttribute = iterator.next();
            //访问该请求url需要的角色信息
            String needRole = configAttribute.getAttribute();
            //如果只是登陆就能访问，即没有匹配到资源信息
            if ("ROLE_login".equals(needRole)) {
                //判断是否登陆，没有登陆则authentication是AnonymousAuthenticationToken接口实现类的对象
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new BadCredentialsException("未登录");
                } else {
                    return;
                }
            }
            //如果匹配上了资源信息，就拿登陆用户的权限信息来对比是否存在于已匹配的角色集合中
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals(needRole)) {
                    return;
                }
            }
        }
        //如果没有匹配上，则权限不足
        throw new AccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
