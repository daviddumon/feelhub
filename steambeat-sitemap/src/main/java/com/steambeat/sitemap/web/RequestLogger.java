package com.steambeat.sitemap.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestLogger implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain chain) throws ServletException, IOException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        chain.doFilter(req, resp);
    }

    @Override
    public void init(final FilterConfig config) throws ServletException {

    }

}
