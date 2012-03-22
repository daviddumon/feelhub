package com.steambeat.sitemap.web;

import com.steambeat.sitemap.domain.SitemapScheduler;
import org.restlet.ext.servlet.ServerServlet;

import javax.servlet.ServletException;

public class SitemapServlet extends ServerServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        new SitemapScheduler();
    }
}
