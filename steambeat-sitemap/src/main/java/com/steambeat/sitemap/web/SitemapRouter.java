package com.steambeat.sitemap.web;

import org.restlet.Context;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

public class SitemapRouter extends Router {

    public SitemapRouter(final Context context) {
        super(context);
        createRoot();
    }

    private void createRoot() {
        final Directory directory = new Directory(getContext(), "file:///hiram/sitemaps");
        attach("/", directory);
    }
}
