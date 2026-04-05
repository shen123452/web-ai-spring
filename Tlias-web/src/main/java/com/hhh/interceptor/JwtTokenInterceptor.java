package com.hhh.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhh.config.JwtProperties;
import com.hhh.pojo.Result;
import com.hhh.utils.JwtUtils;
import com.hhh.utils.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {
    private static final String TOKEN_HEADER_FALLBACK = "token";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtils jwtUtils;
    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        String token = resolveToken(request);
        if (!StringUtils.hasText(token)) {
            writeUnauthorized(response);
            return false;
        }

        try {
            Claims claims = jwtUtils.parseToken(token);
            request.setAttribute("jwtClaims", claims);
            UserContext.setUsername(claims.get("username", String.class));
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Invalid jwt token: {}", e.getMessage());
            writeUnauthorized(response);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear();
    }

    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(jwtProperties.getHeaderName());
        if (!StringUtils.hasText(token)) {
            token = request.getHeader(TOKEN_HEADER_FALLBACK);
        }
        if (!StringUtils.hasText(token)) {
            return null;
        }
        if (token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        }
        return token;
    }

    private void writeUnauthorized(HttpServletResponse response) throws Exception {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), Result.unauthorized("NOT_LOGIN"));
    }
}
