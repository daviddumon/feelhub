package com.steambeat.test.guice;

import com.google.inject.*;
import com.steambeat.application.SubjectService;
import com.steambeat.domain.analytics.identifiers.uri.UriPathResolver;
import com.steambeat.domain.subject.webpage.WebPageFactory;
import com.steambeat.repositories.SessionProvider;
import com.steambeat.test.FakeUriPathResolver;
import com.steambeat.test.fakeFactories.FakeWebPageFactory;
import com.steambeat.test.fakeRepositories.FakeSessionProvider;
import com.steambeat.test.fakeSearches.*;
import com.steambeat.test.fakeServices.FakeSubjectService;
import com.steambeat.tools.Hiram;
import com.steambeat.web.*;
import com.steambeat.web.search.*;

public class SteambeatModuleForTest extends AbstractModule {

    @Override
    protected void configure() {
        bind(WebPageFactory.class).to(FakeWebPageFactory.class);
        bind(OpenSessionInViewFilter.class).to(FakeOpenSessionInViewFilter.class);
        bind(SessionProvider.class).to(FakeSessionProvider.class);
        bind(OpinionSearch.class).to(FakeOpinionSearch.class);
        bind(StatisticsSearch.class).to(FakeStatisticsSearch.class);
        bind(UriPathResolver.class).to(FakeUriPathResolver.class);
        bind(SubjectService.class).to(FakeSubjectService.class);
        bind(SteambeatBoot.class).to(FakeSteambeatBoot.class);
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
