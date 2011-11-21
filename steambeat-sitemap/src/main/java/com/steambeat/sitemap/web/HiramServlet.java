package com.steambeat.sitemap.web;

import com.steambeat.sitemap.domain.HiramScheduler;
import org.restlet.ext.servlet.ServerServlet;

import javax.servlet.ServletException;

public class HiramServlet extends ServerServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        new HiramScheduler();
    }
}
