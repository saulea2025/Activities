package org.example.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebFilter("/*")
public class AccessFilter implements Filter {
    private static final List<String> AUTHORIZED_ALLOWED_PAGES = Arrays.asList("/login", "/logout", "/users/*", "/activities/*");
    private static final List<String> UNAUTHORIZED_ALLOWED_PAGES = Collections.singletonList("/login");
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String path = httpRequest.getServletPath();
        HttpSession session = httpRequest.getSession(false);

        if (session != null && AUTHORIZED_ALLOWED_PAGES.contains(path)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (session == null) {
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
