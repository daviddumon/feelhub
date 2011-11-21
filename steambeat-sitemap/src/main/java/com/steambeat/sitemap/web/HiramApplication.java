package com.steambeat.sitemap.web;

import org.restlet.*;

import javax.servlet.ServletContext;

public class HiramApplication extends Application {

    public HiramApplication(final Context context) {
        super(context);
    }

    @Override
    public synchronized void start() throws Exception {
        super.start();
    }

    @Override
    public Restlet createInboundRoot() {
        return new HiramRouter(getContext());
    }

    private ServletContext servletContext() {
        return (ServletContext) getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");
    }
}
