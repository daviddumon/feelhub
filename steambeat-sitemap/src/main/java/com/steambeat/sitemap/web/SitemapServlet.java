package com.steambeat.sitemap.web;

import org.restlet.ext.servlet.ServerServlet;

import javax.servlet.ServletException;

public class SitemapServlet extends ServerServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        //new SitemapScheduler();
    }
}
