package com.steambeat.web;

import com.google.inject.Injector;
import com.steambeat.web.guice.GuiceFinder;
import com.steambeat.web.resources.*;
import org.restlet.Context;
import org.restlet.resource.*;
import org.restlet.routing.*;

import java.util.Map;

public class SteambeatRouter extends Router {

    public SteambeatRouter(final Context context, final Injector injector) {
        super(context);
        this.injector = injector;
        setDefaultMatchingQuery(true);
        createRoot();
    }

    @Override
    public Finder createFinder(final Class<?> targetClass) {
        return new GuiceFinder(getContext(), targetClass, injector);
    }

    private void createRoot() {
        attachResources();
    }

    private void attachResources() {
        attach("/", HomeResource.class);
        attach("/sitemap_{number}.xml.gz", SitemapResource.class);
        attach("/lastopinions", LastOpinionsResource.class);
        attach("/feeds", FeedsResource.class);
        attach("/stats:{start}.{end};{granularity}", GlobalStatistics.class);
        attachUriResources();
    }

    private void attachUriResources() {
        attachUriResource("/feeds/{uri}/stats:{start}.{end};{granularity}", FeedStatisticsResource.class);
        attachUriResource("/feeds/{uri}/opinions", FeedOpinionsResource.class);
        attachUriResource("/feeds/{uri}/opinions/{opinion}", FeedOpinionsResource.class);
        final TemplateRoute route = attachUriResource("/feeds/{uri}/{page}", FeedResource.class);
        final Map<String, Variable> variables = route.getTemplate().getVariables();
        variables.put("page", new Variable(Variable.TYPE_DIGIT));
        attachUriResource("/feeds/{uri}", FeedResource.class);
    }

    private TemplateRoute attachUriResource(final String path, final Class<? extends ServerResource> resource) {
        final TemplateRoute route = attach(path, resource);
        final Map<String, Variable> variables = route.getTemplate().getVariables();
        variables.put("uri", new Variable(Variable.TYPE_ALL, "", true, false, true, true));
        return route;
    }

    private Injector injector;
}
