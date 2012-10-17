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
        attach("/json/statistics", JsonStatisticsResource.class);
        attach("/json/opinions", JsonOpinionsResource.class);
        attach("/json/relations", JsonRelationsResource.class);
        attach("/json/related", JsonRelatedResource.class);
        attach("/json/illustrations", JsonIllustrationsResource.class);
        attach("/json/createopinion", JsonCreateOpinionResource.class);
        attach("/json/newopinions", JsonNewOpinionsResource.class);

        attachUriForKeywordAndLanguage();
        attachUriForKeyword();

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

    private void attachUriForKeywordAndLanguage() {
        final TemplateRoute route = attach("/topic/{language}/{keyword}", KeywordResource.class);
        final Map<String, Variable> variables = route.getTemplate().getVariables();
        variables.put("keyword", new Variable(Variable.TYPE_URI_ALL, "", true, false, true, true));
        variables.put("language", new Variable(Variable.TYPE_ALPHA, "", true, false, true, true));
    }

    private void attachUriForKeyword() {
        final TemplateRoute route = attach("/topic/{keyword}", KeywordResource.class);
        final Map<String, Variable> variables = route.getTemplate().getVariables();
        variables.put("keyword", new Variable(Variable.TYPE_URI_ALL, "", true, false, true, true));
    }

    @Override
    public Finder createFinder(final Class<?> targetClass) {
        return new GuiceFinder(getContext(), targetClass, injector);
    }

    private final Injector injector;
}
