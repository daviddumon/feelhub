package com.steambeat.web;

import com.google.inject.Injector;
import com.steambeat.web.guice.GuiceFinder;
import com.steambeat.web.resources.*;
import org.restlet.Context;
import org.restlet.resource.Finder;
import org.restlet.routing.Router;

public class SteambeatRouter extends Router {

    public SteambeatRouter(final Context context, final Injector injector) {
        super(context);
        this.injector = injector;
        setDefaultMatchingMode(MODE_FIRST_MATCH);
        attachResources();
    }

    private void attachResources() {
        //attach("/webpages/{id}/stats:{start}.{end};{granularity}", WebPageStatisticsResource.class);
        attach("/webpages/{id}", WebPageResource.class);
        attach("/webpages", WebPagesResource.class);
        //attach("/stats:{start}.{end};{granularity}", HomeStatisticsResource.class);
        attach("/opinions", OpinionsResource.class);
        attach("/sitemap_{number}.xml.gz", SitemapResource.class);
        attach("/bookmarklet", BookmarkletResource.class);
        attach("/", HomeResource.class);
    }

    @Override
    public Finder createFinder(final Class<?> targetClass) {
        return new GuiceFinder(getContext(), targetClass, injector);
    }

    private final Injector injector;
}
