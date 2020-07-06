package com.example.lixc.config.security.config;

import com.example.lixc.util.ResultJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lixc
 * @Description
 * @createTime 2020/6/14 22:56
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest req, HttpServletResponse rep, AccessDeniedException e) throws IOException, ServletException {
        rep.setContentType("application/json;charset=UTF-8");
        PrintWriter out = rep.getWriter();
        Map<String, String> result = new HashMap<>();
        result.put("status", "403");
        result.put("message", "权限不足");
        result.put("data", e.getMessage());
        out.write(new ObjectMapper().writeValueAsString(result));
        out.flush();
        out.close();
    }
}
