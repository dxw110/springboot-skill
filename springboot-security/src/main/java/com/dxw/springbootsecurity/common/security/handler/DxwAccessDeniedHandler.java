package com.dxw.springbootsecurity.common.security.handler;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 无权限返回处理器
 *
 * @Author: Dxw
 * @Date: 2020/6/1 11:02
 */
@Component
public class DxwAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    public DxwAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write("You do not have this operation right now, please contact the administrator\n您暂无该操作权限，请联系管理员");
    }
}
