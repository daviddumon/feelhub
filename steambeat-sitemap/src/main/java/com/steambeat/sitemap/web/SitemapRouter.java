package com.steambeat.sitemap.web;

import com.steambeat.sitemap.web.resources.*;
import org.restlet.Context;
import org.restlet.routing.*;

public class SitemapRouter extends Router {

    public SitemapRouter(final Context context) {
        super(context);
        setDefaultMatchingMode(MODE_BEST_MATCH);
        attachResources();
    }

    private void attachResources() {
        attach("/robots.txt", RobotsResource.class);
        attach("/sitemap_index_{index}.xml", SitemapIndexResource.class);
        attach("/sitemap_{index}.xml", SitemapResource.class);
        attachDefault(RedirectResource.class);
    }
}
