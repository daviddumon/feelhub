package com.steambeat.test.guice;

import com.google.inject.*;
import com.steambeat.application.AssociationService;
import com.steambeat.domain.subject.webpage.WebPageFactory;
import com.steambeat.repositories.SessionProvider;
import com.steambeat.test.fakeFactories.FakeWebPageFactory;
import com.steambeat.test.fakeRepositories.FakeSessionProvider;
import com.steambeat.test.fakeServices.FakeAssociationService;
import com.steambeat.tools.Hiram;
import com.steambeat.web.OpenSessionInViewFilter;

public class SteambeatModuleForTest extends AbstractModule {

    @Override
    protected void configure() {
        bind(WebPageFactory.class).to(FakeWebPageFactory.class);
        bind(AssociationService.class).to(FakeAssociationService.class);
        bind(OpenSessionInViewFilter.class).to(FakeOpenSessionInViewFilter.class);
        bind(SessionProvider.class).to(FakeSessionProvider.class);
    }

    @Provides
    public Hiram getHiram() {
        if (hiram == null) {
            return new Hiram();
        }
        return hiram;
    }

    public void setHiram(final Hiram hiram) {
        this.hiram = hiram;
    }

    private Hiram hiram;
}
