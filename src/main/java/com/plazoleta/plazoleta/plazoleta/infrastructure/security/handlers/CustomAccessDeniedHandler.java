package com.plazoleta.plazoleta.plazoleta.infrastructure.security.handlers;

import com.plazoleta.plazoleta.plazoleta.infrastructure.security.utils.SecurityConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(SecurityConstants.APPLICATION_JSON);

        String jsonResponse = SecurityConstants.ACCESS_DENIED_MESSAGE_TEMPLATE.formatted(LocalDateTime.now());

        response.getWriter().write(jsonResponse);
    }
}
