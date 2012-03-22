package com.steambeat.sitemap.web;

import com.steambeat.sitemap.web.resources.RobotsResource;
import org.restlet.Context;
import org.restlet.routing.Router;

public class SitemapRouter extends Router {

    public SitemapRouter(final Context context) {
        super(context);
        setDefaultMatchingMode(MODE_FIRST_MATCH);
        attachResources();
    }

    private void attachResources() {
        attach("/robots.txt", RobotsResource.class);
    }
}
