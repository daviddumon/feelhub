package com.feelhub.web;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.web.guice.GuiceTestModule;
import com.feelhub.web.tools.FeelhubSitemapModuleLink;
import com.google.common.eventbus.EventBus;
import org.junit.rules.ExternalResource;
import org.restlet.Context;

public class WebApplicationTester extends ExternalResource {

    public WebApplicationTester() {
    }

    @Override
    protected void before() throws Throwable {
        DomainEventBus.INSTANCE.setEventBus(new EventBus());
        final Context context = ContextTestFactory.buildContext();
        application = new FeelhubApplication(context);
        application.initializeGuice(moduleGuiceTestModule);
        application.start();
    }

    public ClientResource newClientResource(final String uri) {
        return new ClientResource(uri, application);
    }

    public void setSitemapLink(final FeelhubSitemapModuleLink feelhubSitemapModuleLink) {
        moduleGuiceTestModule.setFeelhubSitemapModuleLink(feelhubSitemapModuleLink);
    }

    public FeelhubApplication getApplication() {
        return application;
    }

    private FeelhubApplication application;
    private final GuiceTestModule moduleGuiceTestModule = new GuiceTestModule();
}