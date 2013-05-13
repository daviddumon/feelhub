package com.feelhub.web;

import com.feelhub.web.guice.GuiceFinder;
import com.feelhub.web.resources.*;
import com.feelhub.web.resources.admin.*;
import com.feelhub.web.resources.admin.analytic.*;
import com.feelhub.web.resources.api.*;
import com.feelhub.web.resources.authentification.*;
import com.feelhub.web.resources.sitemap.*;
import com.feelhub.web.resources.social.*;
import com.google.inject.Injector;
import org.restlet.*;
import org.restlet.data.ChallengeScheme;
import org.restlet.resource.*;
import org.restlet.routing.*;
import org.restlet.security.*;

import java.util.Map;

public class FeelhubRouter extends Router {

    public static String ADMIN_USER = "feelhub";
    public static String ADMIN_PASSWORD = "youshallnotpass";

    public FeelhubRouter(final Context context, final Injector injector) {
        super(context);
        this.injector = injector;
        setDefaultMatchingMode(MODE_FIRST_MATCH);
        attachResources();
    }

    private void attachResources() {
        attachApiResources();
        attachWebResources();
        attachAdminResources();
    }

    private void attachApiResources() {
        attach("/api/topics", ApiTopicsResource.class);
        attach("/api/topic/{topicId}/statistics", ApiTopicStatisticsResource.class);
        attach("/api/topic/{topicId}/related", ApiTopicRelatedResource.class);
        attach("/api/topic/{topicId}/feelings", ApiTopicFeelingsResource.class);
        attach("/api/topic/{topicId}/context", ApiTopicContextResource.class);
        attach("/api/topic/{topicId}/feeling/{feelingId}/new", ApiTopicNewFeelingsResource.class);
        attach("/api/feelings", ApiFeelingsResource.class);
    }

    private void attachWebResources() {
        attach("/robots.txt", FeelhubRobotsResource.class);
        attach("/topic/{topicId}", TopicResource.class);
        attach("/signup", SignupResource.class);
        attach("/sessions", SessionsResource.class);
        attach("/activation/{secret}", ActivationResource.class);
        attach("/login", LoginResource.class);
        attach("/help", HelpResource.class);
        attach("/welcome", WelcomeResource.class);
        attach("/search", SearchResource.class);
        attach("/newtopic", NewTopicResource.class);
        attach("/sitemap_index_{number}.xml", FeelhubSitemapIndexResource.class);
        attach("/sitemap_{number}.xml", FeelhubSitemapResource.class);
        attach("/social/facebook", FacebookResource.class);
        attach("/social/google-signup", GoogleSignupResource.class);
        attach("/social/google", GoogleResource.class);
        attach("/social/welcome", SocialWelcomeResource.class);
        attach("/bookmarklet", BookmarkletResource.class);
        attach("/", HomeResource.class);
    }

    private void attachAdminResources() {
        final Router router = new Router(getContext()) {
            @Override
            public Finder createFinder(final Class<?> targetClass) {
                return new GuiceFinder(getContext(), targetClass, injector);
            }
        };
        router.attach("/admin", AdminResource.class);
        router.attach("/admin/analytic/live", DailyLiveStatisticsResource.class);
        router.attach("/admin/analytic/newuser", NewUserDailyBehaviorResource.class);
        router.attach("/admin/analytic/dailybehavior", ActiveUserDailyBehaviorResource.class);
        router.attach("/admin/ftl/{name}", AdminFreemarkerResource.class);
        router.attach("/admin/events", AdminEventsResource.class);
        router.attach("/admin/statistics", AdminStatisticsResource.class);
        attach(withSecurity(router));
    }

    private ChallengeAuthenticator withSecurity(final Restlet next) {
        final MapVerifier verifier = new MapVerifier();
        verifier.getLocalSecrets().put(ADMIN_USER, ADMIN_PASSWORD.toCharArray());
        final ChallengeAuthenticator sécurité = new ChallengeAuthenticator(getContext(),
                ChallengeScheme.HTTP_BASIC, "Secured Area");
        sécurité.setVerifier(verifier);
        sécurité.setNext(next);
        return sécurité;
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
