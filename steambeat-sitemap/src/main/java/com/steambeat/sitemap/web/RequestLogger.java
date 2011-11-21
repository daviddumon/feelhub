package com.steambeat.sitemap.web;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestLogger implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        logger.info("Request -> HOST : " + httpServletRequest.getHeader("\"X-Forwarded-For\"") + " - USER : " + httpServletRequest.getHeader("Host") + " - FILE : " + httpServletRequest.getRequestURI());
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    private static final Logger logger = Logger.getLogger(RequestLogger.class);
}
