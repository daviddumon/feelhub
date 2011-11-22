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
        attach("/opinions", OpinionsResource.class);
        attach("/opinions?{query}", OpinionsResource.class);
        attach("/webpages", WebPagesResource.class);
        attach("/stats:{start}.{end};{granularity}", GlobalStatistics.class);
        attachUriResources();
    }

    private void attachUriResources() {
        attachUriResource("/webpages/{uri}/stats:{start}.{end};{granularity}", WebPageStatisticsResource.class);
        attachUriResource("/webpages/{uri}/opinions", WebPageOpinionsResource.class);
        attachUriResource("/webpages/{uri}/opinions/{opinion}", WebPageOpinionsResource.class);
        final TemplateRoute route = attachUriResource("/webpages/{uri}/{page}", WebPageResource.class);
        final Map<String, Variable> variables = route.getTemplate().getVariables();
        variables.put("page", new Variable(Variable.TYPE_DIGIT));
        attachUriResource("/webpages/{uri}", WebPageResource.class);
    }

    private TemplateRoute attachUriResource(final String path, final Class<? extends ServerResource> resource) {
        final TemplateRoute route = attach(path, resource);
        final Map<String, Variable> variables = route.getTemplate().getVariables();
        variables.put("uri", new Variable(Variable.TYPE_ALL, "", true, false, true, true));
        return route;
    }

    private final Injector injector;
}
