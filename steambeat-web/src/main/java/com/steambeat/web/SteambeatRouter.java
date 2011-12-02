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

    private void createRoot() {
        attachResources();
    }

    private void attachResources() {
        attachUriResource("/webpages/{uri}/stats:{start}.{end};{granularity}", WebPageStatisticsResource.class);
        attachUriResource("/webpages/{uri}/opinions", WebPageOpinionsResource.class);
        attachUriResource("/webpages/{uri}", WebPageResource.class);
        attach("/webpages", WebPagesResource.class);
        attach("/stats:{start}.{end};{granularity}", HomeStatisticsResource.class);
        attach("/opinions;{skip};{limit}", HomeOpinionsResource.class);
        attach("/opinions", OpinionsResource.class);
        attach("/sitemap_{number}.xml.gz", SitemapResource.class);
        attach("/about", AboutResource.class);
        attach("/terms", TermsResource.class);
        attach("/", HomeResource.class);
    }

    private TemplateRoute attachUriResource(final String path, final Class<? extends ServerResource> resource) {
        final TemplateRoute route = attach(path, resource);
        final Map<String, Variable> variables = route.getTemplate().getVariables();
        variables.put("uri", new Variable(Variable.TYPE_ALL, "", true, false, true, true));
        return route;
    }

    @Override
    public Finder createFinder(final Class<?> targetClass) {
        return new GuiceFinder(getContext(), targetClass, injector);
    }

    private final Injector injector;
}
