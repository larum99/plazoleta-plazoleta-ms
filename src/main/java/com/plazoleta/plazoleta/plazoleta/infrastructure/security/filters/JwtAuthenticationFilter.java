package com.plazoleta.plazoleta.plazoleta.infrastructure.security.filters;

import com.plazoleta.plazoleta.plazoleta.infrastructure.security.utils.JwtUtil;
import com.plazoleta.plazoleta.plazoleta.infrastructure.security.utils.ResponseConstants;
import com.plazoleta.plazoleta.plazoleta.infrastructure.security.utils.SecurityConstants;
import com.plazoleta.plazoleta.plazoleta.infrastructure.security.utils.SecurityMessages;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
        String token = null;
        Long userId = null;
        String role = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(SecurityConstants.BEARER_PREFIX)) {
            token = authorizationHeader.substring(SecurityConstants.BEARER_PREFIX.length());

            try {
                userId = jwtUtil.extractUserId(token);
                role = jwtUtil.extractRole(token);
            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType(ResponseConstants.CONTENT_TYPE_JSON);
                response.getWriter().write("{\"" + ResponseConstants.MESSAGE_KEY + "\":\"" +
                        SecurityMessages.EXPIRED_TOKEN_MESSAGE + "\",\"" +
                        ResponseConstants.TIMESTAMP_KEY + "\":\"" + LocalDateTime.now() + "\"}");
                return;
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(ResponseConstants.CONTENT_TYPE_JSON);
                response.getWriter().write("{\"" + ResponseConstants.MESSAGE_KEY + "\":\"" +
                        SecurityMessages.INVALID_TOKEN_MESSAGE + "\",\"" +
                        ResponseConstants.TIMESTAMP_KEY + "\":\"" + LocalDateTime.now() + "\"}");
                return;
            }
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + role))
                    );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
