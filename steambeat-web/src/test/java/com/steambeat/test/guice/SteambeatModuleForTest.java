package com.steambeat.test.guice;

import com.google.inject.*;
import com.steambeat.application.SubjectService;
import com.steambeat.domain.analytics.identifiers.uri.UriPathResolver;
import com.steambeat.domain.scrapers.UriScraper;
import com.steambeat.domain.subject.webpage.WebPageFactory;
import com.steambeat.repositories.SessionProvider;
import com.steambeat.test.*;
import com.steambeat.test.fakeFactories.FakeWebPageFactory;
import com.steambeat.test.fakeRepositories.FakeSessionProvider;
import com.steambeat.test.fakeSearches.*;
import com.steambeat.test.fakeServices.FakeSubjectService;
import com.steambeat.tools.SitemapLink;
import com.steambeat.web.*;
import com.steambeat.web.migration.MigrationRunner;
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
        bind(MigrationRunner.class).to(FakeMigrationRunner.class);
        bind(UriScraper.class).to(FakeUriScraper.class);
    }

    @Provides
    public SitemapLink getSitemapLink() {
        if (sitemapLink == null) {
            return new SitemapLink();
        }
        return sitemapLink;
    }

    public void setSitemapLink(final SitemapLink sitemapLink) {
        this.sitemapLink = sitemapLink;
    }

    private SitemapLink sitemapLink;
}
