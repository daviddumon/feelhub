package com.steambeat.test.guice;

import com.google.inject.*;
import com.steambeat.application.AssociationService;
import com.steambeat.domain.subject.feed.FeedFactory;
import com.steambeat.test.fakeFactories.FakeFeedFactory;
import com.steambeat.test.fakeSearches.FakeOpinionSearch;
import com.steambeat.test.fakeServices.FakeAssociationService;
import com.steambeat.tools.Hiram;
import com.steambeat.web.OpenSessionInViewFilter;
import com.steambeat.web.search.OpinionSearch;

public class SteambeatModuleForTest extends AbstractModule {

    @Override
    protected void configure() {
        bind(FeedFactory.class).to(FakeFeedFactory.class);
        bind(AssociationService.class).to(FakeAssociationService.class);
        bind(OpenSessionInViewFilter.class).to(FakeOpenSessionInViewFilter.class);
        bind(OpinionSearch.class).to(FakeOpinionSearch.class);
    }

    @Provides
    public Hiram getHiram() {
        if (hiram == null) {
            return new Hiram();
        }
        return hiram;
    }

    public void setHiram(Hiram hiram) {
        this.hiram = hiram;
    }

    private Hiram hiram;
}
