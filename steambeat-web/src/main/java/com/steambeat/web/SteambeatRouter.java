package com.steambeat.web;

import com.google.inject.Injector;
import com.steambeat.web.guice.GuiceFinder;
import com.steambeat.web.resources.*;
import com.steambeat.web.resources.authentification.*;
import com.steambeat.web.resources.json.*;
import com.steambeat.web.resources.staticftl.FreemarkerResource;
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
        attach("/ftl/{name}", FreemarkerResource.class);
        attach("/statistics", StatisticsResource.class);
        attach("/webpages/{id}", WebPageResource.class);
        attach("/concepts/{id}", ConceptResource.class);
        attach("/webpages", WebPagesResource.class);
        attach("/opinions", OpinionsResource.class);
        attach("/related", RelatedResource.class);
        attach("/association", AssociationResource.class);
        attach("/sitemap_index_{number}.xml", SteambeatSitemapIndexResource.class);
        attach("/sitemap_{number}.xml", SteambeatSitemapResource.class);
        attach("/bookmarklet", BookmarkletResource.class);
        attach("/signup", SignupResource.class);
        attach("/sessions", SessionsResource.class);
        attach("/activation/{secret}", ActivationResource.class);
        attach("/login", LoginResource.class);
        attach("/help", HelpResource.class);
        attach("/welcome", WelcomeResource.class);
        attach("/", HomeResource.class);
    }

    @Override
    public Finder createFinder(final Class<?> targetClass) {
        return new GuiceFinder(getContext(), targetClass, injector);
    }

    private final Injector injector;
}
