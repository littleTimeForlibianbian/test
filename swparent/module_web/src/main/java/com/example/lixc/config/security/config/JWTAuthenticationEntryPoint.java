package com.example.lixc.config.security.config;

import com.example.lixc.enums.UserSecurityEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lixc
 * @Description 这里是认证权限入口 -> 即在未登录的情况下访问所有接口都会拦截到此（除了放行忽略接口
 * @createTime 2020/6/14 16:58
 */
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Map<String, Object> result = new HashMap<>();
        result.put("status", UserSecurityEnum.USER_NEED_AUTHORITIES.getCode());
        result.put("message", UserSecurityEnum.USER_NEED_AUTHORITIES.getMessage());
        result.put("data", authException.getMessage());
        out.write(new ObjectMapper().writeValueAsString(result));
        out.flush();
        out.close();
    }
}
