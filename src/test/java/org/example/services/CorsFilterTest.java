package org.example.services;

import org.example.filters.AccessFilter;
import org.example.filters.CorsFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class CorsFilterTest {
    private CorsFilter corsFilter;
    private HttpServletRequest httpServletRequestMock;
    private HttpServletResponse httpServletResponseMock;
    private FilterChain filterChainMock;

    @BeforeEach
    public void setUp() {
        corsFilter = new CorsFilter();
        httpServletRequestMock = mock(HttpServletRequest.class);
        httpServletResponseMock = mock(HttpServletResponse.class);
        filterChainMock = mock(FilterChain.class);
    }

    @Test
    public void testOptionsRequestReturnsOkStatus() throws ServletException, IOException {

        when(httpServletRequestMock.getMethod()).thenReturn("OPTIONS");

        corsFilter.doFilter(httpServletRequestMock, httpServletResponseMock, filterChainMock);

        verify(httpServletResponseMock).setStatus(HttpServletResponse.SC_OK);
        verify(filterChainMock, never()).doFilter(httpServletRequestMock, httpServletResponseMock);
    }

    @Test
    public void testNonOptionsRequestContinuesFilterChain() throws ServletException, IOException {

        when(httpServletRequestMock.getMethod()).thenReturn("GET");

        corsFilter.doFilter(httpServletRequestMock, httpServletResponseMock, filterChainMock);

        verify(filterChainMock).doFilter(httpServletRequestMock, httpServletResponseMock);
        verify(httpServletResponseMock, never()).setStatus(HttpServletResponse.SC_OK);
    }
}