package com.griddynamics.springsecuritybasic.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthenticationRequestValidationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String header = request.getHeader("Authorization");
        if (header != null) {
            header = header.trim();
            if (header.equalsIgnoreCase("Basic")) {
                throw new BadCredentialsException("Empty basic authentication token");
            } else if (StringUtils.startsWithIgnoreCase(header, "Basic")) {
                try {
                    byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
                    byte[] decoded = Base64.getDecoder().decode(base64Token);
                    String token = new String(decoded, request.getCharacterEncoding());
                    int delim = token.indexOf(":");
                    if (delim == -1) {
                        throw new BadCredentialsException("Invalid basic authentication token");
                    }
                    String email = token.substring(0, delim);
                    if (email.toLowerCase().contains("test")) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                } catch (IllegalArgumentException iae) {
                    throw new BadCredentialsException("Failed to decode basic authentication token");
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
