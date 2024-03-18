package org.example.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.example.filters.AccessFilter;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class AccessFilterTest {
    private AccessFilter accessFilter;
    private HttpServletRequest httpServletRequestMock;
    private HttpServletResponse httpServletResponseMock;
    private FilterChain filterChainMock;
    private HttpSession httpSessionMock;

    @BeforeEach
    public void setUp() {
        accessFilter = new AccessFilter();
        httpServletRequestMock = mock(HttpServletRequest.class);
        httpServletResponseMock = mock(HttpServletResponse.class);
        filterChainMock = mock(FilterChain.class);
        httpSessionMock = mock(HttpSession.class);
        when(httpServletRequestMock.getSession()).thenReturn(httpSessionMock);
    }
    /*@Test
    @Ignore
    public void testAccessToAuthorizedPageWithAuthentication() throws ServletException, IOException {

        when(httpServletRequestMock.getServletPath()).thenReturn("/users"); // An authorized page
        when(httpServletRequestMock.getSession()).thenReturn(httpSessionMock);
        when(httpSessionMock.getAttribute("person")).thenReturn(new Object()); // Simulating authentication

        accessFilter.doFilter(httpServletRequestMock, httpServletResponseMock, filterChainMock);

        verify(filterChainMock).doFilter(httpServletRequestMock, httpServletResponseMock);
        verify(httpServletResponseMock, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED); // No 401 status should be set
    }

    @Test
    @Ignore
    public void testAccessToAuthorizedPageWithoutAuthentication() throws ServletException, IOException {

        when(httpServletRequestMock.getServletPath()).thenReturn("/users");

        accessFilter.doFilter(httpServletRequestMock, httpServletResponseMock, filterChainMock);

        verify(filterChainMock, never()).doFilter(httpServletRequestMock, httpServletResponseMock);
        verify(httpServletResponseMock).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }*/

    @Test
    public void testAccessToUnauthorizedPageWithAuthentication() throws ServletException, IOException {

        when(httpServletRequestMock.getServletPath()).thenReturn("/login");
        when(httpServletRequestMock.getSession()).thenReturn(httpSessionMock);
        when(httpSessionMock.getAttribute("person")).thenReturn(new Object());

        accessFilter.doFilter(httpServletRequestMock, httpServletResponseMock, filterChainMock);

        verify(filterChainMock).doFilter(httpServletRequestMock, httpServletResponseMock);
        verify(httpServletResponseMock, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    public void testAccessToUnauthorizedPageWithoutAuthentication() throws ServletException, IOException {

        when(httpServletRequestMock.getServletPath()).thenReturn("/login");

        accessFilter.doFilter(httpServletRequestMock, httpServletResponseMock, filterChainMock);

        verify(filterChainMock).doFilter(httpServletRequestMock, httpServletResponseMock);
        verify(httpServletResponseMock, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
