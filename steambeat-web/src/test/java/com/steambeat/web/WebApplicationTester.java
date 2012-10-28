package com.steambeat.web;

import com.google.common.eventbus.EventBus;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.web.guice.GuiceTestModule;
import com.steambeat.web.tools.SteambeatSitemapModuleLink;
import org.junit.rules.ExternalResource;
import org.restlet.Context;

public class WebApplicationTester extends ExternalResource {

	public WebApplicationTester() {
	}

	@Override
    protected void before() throws Throwable {
        DomainEventBus.INSTANCE.setEventBus(new EventBus());
        Context context = ContextTestFactory.buildContext();
        application = new SteambeatApplication(context);
        application.initializeGuice(moduleGuiceTestModule);
        application.start();
    }

	public ClientResource newClientResource(final String uri) {
        return new ClientResource(uri, application);
    }

    public void setSitemapLink(final SteambeatSitemapModuleLink steambeatSitemapModuleLink) {
        moduleGuiceTestModule.setSteambeatSitemapModuleLink(steambeatSitemapModuleLink);
    }

    public SteambeatApplication getApplication() {
        return application;
    }

    public GuiceTestModule getModuleGuiceTestModule() {
        return moduleGuiceTestModule;
    }

	private SteambeatApplication application;
    private GuiceTestModule moduleGuiceTestModule = new GuiceTestModule();
}
