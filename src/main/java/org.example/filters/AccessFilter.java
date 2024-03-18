package org.example.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;

@WebFilter("/*")
public class AccessFilter implements Filter {
    private static final List<String> AUTHORIZED_ALLOWED_PAGES = Arrays.asList("/login", "/logout", "/users", "/activities");
    private static final List<String> UNAUTHORIZED_ALLOWED_PAGES = Collections.singletonList("/login");
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String path = httpRequest.getServletPath();
        HttpSession session = httpRequest.getSession();
        if (nonNull(session.getAttribute("person")) && (AUTHORIZED_ALLOWED_PAGES.contains(path) || path.startsWith("/activities/") || path.startsWith("/users/"))){
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        else {
            if(UNAUTHORIZED_ALLOWED_PAGES.contains(path)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            else httpResponse.setStatus(401);
        }
    }

    @Override
    public void destroy() {

    }
}
