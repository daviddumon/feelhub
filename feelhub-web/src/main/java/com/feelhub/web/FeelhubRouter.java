package com.feelhub.web;

import com.feelhub.web.guice.GuiceFinder;
import com.feelhub.web.resources.*;
import com.feelhub.web.resources.admin.*;
import com.feelhub.web.resources.api.*;
import com.feelhub.web.resources.authentification.*;
import com.feelhub.web.resources.social.*;
import com.google.inject.Injector;
import org.restlet.Context;
import org.restlet.resource.*;
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
        attach("/api/uri/{id}/statistics", ApiUriStatisticsResource.class);
        attachEncodedValue("/api/uri/{value}", ApiUriResource.class);

        attach("/api/word", ApiWordResource.class);
        attach("/api/feelings", ApiFeelingsResource.class);
        attach("/api/related", ApiRelatedResource.class);
        attach("/api/illustrations", ApiIllustrationsResource.class);
        attach("/api/createfeeling", ApiCreateFeelingResource.class);
        attach("/api/newfeelings", ApiNewFeelingsResource.class);

        attachEncodedValue("/uri/{value}", UriResource.class);

        attach("/word/{language}/{keywordValue}", WordResource.class);
        attach("/word/{keywordValue}", WordResource.class);

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
        attach("/social/welcome", SocialWelcomeResource.class);

        attach("/", HomeResource.class);
    }

    private void attachEncodedValue(final String pathTemplate, final Class<? extends ServerResource> targetClass) {
        final TemplateRoute route = attach(pathTemplate, targetClass);
        final Map<String, Variable> variables = route.getTemplate().getVariables();
        variables.put("value", new Variable(Variable.TYPE_URI_ALL, "", true, false, true, true));
    }

    @Override
    public Finder createFinder(final Class<?> targetClass) {
        return new GuiceFinder(getContext(), targetClass, injector);
    }

    private final Injector injector;
}
