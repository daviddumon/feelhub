package com.steambeat.web;

import com.google.inject.Injector;
import com.steambeat.web.guice.GuiceFinder;
import com.steambeat.web.resources.*;
import com.steambeat.web.resources.admin.*;
import com.steambeat.web.resources.authentification.*;
import com.steambeat.web.resources.json.*;
import org.restlet.Context;
import org.restlet.resource.Finder;
import org.restlet.routing.*;

import java.util.Map;

public class SteambeatRouter extends Router {

    public SteambeatRouter(final Context context, final Injector injector) {
        super(context);
        this.injector = injector;
        setDefaultMatchingMode(MODE_FIRST_MATCH);
        attachResources();
    }

    private void attachResources() {
        attach("/json/statistics", StatisticsResource.class);
        attach("/json/opinions", OpinionsResource.class);
        attach("/json/related", RelatedResource.class);

        attachUri("/topic/{language}/{keyword}", KeywordResource.class);
        attachUri("/topic/{keyword}", KeywordResource.class);

        attach("/admin/ftl/{name}", AdminFreemarkerResource.class);
        attach("/admin/events", AdminEventsResource.class);

        attach("/signup", SignupResource.class);
        attach("/sessions", SessionsResource.class);
        attach("/activation/{secret}", ActivationResource.class);
        attach("/login", LoginResource.class);
        attach("/help", HelpResource.class);
        attach("/welcome", WelcomeResource.class);

        attach("/sitemap_index_{number}.xml", SteambeatSitemapIndexResource.class);
        attach("/sitemap_{number}.xml", SteambeatSitemapResource.class);

        attach("/", HomeResource.class);
    }

    private void attachUri(final String pathTemplate, final Class<KeywordResource> targetClass) {
        final TemplateRoute route = attach(pathTemplate, targetClass);
        final Map<String, Variable> variables = route.getTemplate().getVariables();
        variables.put("keyword", new Variable(Variable.TYPE_URI_ALL, "", true, false, true, true));
    }

    @Override
    public Finder createFinder(final Class<?> targetClass) {
        return new GuiceFinder(getContext(), targetClass, injector);
    }

    private final Injector injector;
}
