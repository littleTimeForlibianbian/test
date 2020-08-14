package com.example.lixc.config.security.config;

import com.example.lixc.config.security.entity.JwtUser;
import com.example.lixc.config.security.utils.JwtTokenUtils;
import com.example.lixc.config.token.TokenUtils;
import com.example.lixc.enums.UserSecurityEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: AjaxAuthenticationSuccessHandler
 * @description: 用户登录成功时返回给前端的数据
 * @Author: Wilson
 * @createTime 2020/8/4 23:05
 */
@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    private TokenUtils tokenUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        String token = tokenUtils.createToken(jwtUser.getUsername());
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Map<String, Object> result = new HashMap<>();
        result.put("status", UserSecurityEnum.USER_LOGIN_SUCCESS.getCode());
        result.put("message", UserSecurityEnum.USER_LOGIN_SUCCESS.getMessage());
        result.put("data", token);
        out.write(new ObjectMapper().writeValueAsString(result));
        out.flush();
        out.close();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

    }
}
