package com.feelhub.web;

import com.feelhub.web.guice.GuiceFinder;
import com.feelhub.web.resources.*;
import com.feelhub.web.resources.admin.*;
import com.feelhub.web.resources.authentification.*;
import com.feelhub.web.resources.json.*;
import com.feelhub.web.resources.social.FacebookResource;
import com.google.inject.Injector;
import org.restlet.Context;
import org.restlet.resource.Finder;
import org.restlet.routing.*;

import java.util.Map;

public class FeelhubRouter extends Router {

    public FeelhubRouter(final Context context, final Injector injector) {
        super(context);
        this.injector = injector;
        setDefaultMatchingMode(MODE_FIRST_MATCH);
        attachResources();
    }

    private void attachResources() {
        attach("/json/statistics", JsonStatisticsResource.class);
        attach("/json/feelings", JsonFeelingsResource.class);
        attach("/json/relations", JsonRelationsResource.class);
        attach("/json/related", JsonRelatedResource.class);
        attach("/json/illustrations", JsonIllustrationsResource.class);
        attach("/json/createfeeling", JsonCreateFeelingResource.class);
        attach("/json/newfeelings", JsonNewFeelingsResource.class);
        attach("/json/keyword", JsonKeywordResource.class);

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

        attach("/sitemap_index_{number}.xml", FeelhubSitemapIndexResource.class);
        attach("/sitemap_{number}.xml", FeelhubSitemapResource.class);
        attach("/social/facebook", FacebookResource.class);

        attach("/", HomeResource.class);
    }

    private void attachUriForKeywordAndLanguage() {
        final TemplateRoute route = attach("/topic/{language}/{keyword}", KeywordResource.class);
        final Map<String, Variable> variables = route.getTemplate().getVariables();
        variables.put("language", new Variable(Variable.TYPE_ALPHA, "", true, false, true, true));
        variables.put("keyword", new Variable(Variable.TYPE_URI_ALL, "", true, false, true, true));
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
