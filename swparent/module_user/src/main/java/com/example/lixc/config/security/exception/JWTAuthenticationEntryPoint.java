package com.example.lixc.config.security.exception;

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
 * @Description 没有携带token或者token无效 超时
 * @createTime 2020/6/14 16:58
 */
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json; charset=utf-8");
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        String reason = "统一处理，原因：" + authException.getMessage();
//        response.getWriter().write(new ObjectMapper().writeValueAsString(reason));
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Map<String, String> result = new HashMap<>();
        result.put("status", "403");
        result.put("message", "权限不足");
        result.put("data", authException.getMessage());
        out.write(new ObjectMapper().writeValueAsString(result));
        out.flush();
        out.close();
    }
}
