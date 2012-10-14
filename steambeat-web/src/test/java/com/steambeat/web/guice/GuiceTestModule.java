package com.steambeat.web.guice;

import com.google.inject.*;
import com.steambeat.application.*;
import com.steambeat.domain.alchemy.*;
import com.steambeat.domain.bingsearch.*;
import com.steambeat.domain.keyword.KeywordFactory;
import com.steambeat.domain.reference.ReferenceFactory;
import com.steambeat.domain.scraper.*;
import com.steambeat.domain.translation.*;
import com.steambeat.domain.uri.*;
import com.steambeat.repositories.SessionProvider;
import com.steambeat.repositories.fakeRepositories.FakeSessionProvider;
import com.steambeat.web.filter.*;
import com.steambeat.web.mail.*;
import com.steambeat.web.migration.*;
import com.steambeat.web.migration.web.MigrationFilter;
import com.steambeat.web.search.*;
import com.steambeat.web.search.fake.*;
import com.steambeat.web.tools.SteambeatSitemapModuleLink;

import static org.mockito.Mockito.*;

public class GuiceTestModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(OpenSessionInViewFilter.class).to(FakeOpenSessionInViewFilter.class);
        bind(SessionProvider.class).to(FakeSessionProvider.class);
        bind(OpinionSearch.class).to(FakeOpinionSearch.class);
        bind(StatisticsSearch.class).to(FakeStatisticsSearch.class);
        bind(RelationSearch.class).to(FakeRelationSearch.class);
        bind(IllustrationSearch.class).to(FakeIllustrationSearch.class);
        bind(UriResolver.class).to(FakeUriResolver.class);
        bind(MigrationRunner.class).to(FakeMigrationRunner.class);
        bind(MigrationFilter.class).to(FakeMigrationFilter.class);
        bind(Scraper.class).to(FakeScraper.class);
        bind(NamedEntityProvider.class).toInstance(new NamedEntityProvider(new FakeJsonAlchemyLink(), new NamedEntityBuilder()));
        bind(Translator.class).to(FakeTranslator.class);
        bind(AlchemyLink.class).to(FakeJsonAlchemyLink.class);
        bind(BingLink.class).to(FakeBingLink.class);
        bind(MailBuilder.class).toInstance(new MailBuilder(new FakeMailSender()));
    }

    @Provides
    public KeywordService keywordService() {
        keywordService = spy(new KeywordService(new ReferenceService(new ReferenceFactory()),new KeywordFactory(),new FakeTranslator(), new UriManager(new FakeUriResolver())));
        return keywordService;
    }

    @Provides
    public SteambeatSitemapModuleLink getSteambeatSitemapModuleLink() {
        if (steambeatSitemapModuleLink == null) {
            return new SteambeatSitemapModuleLink();
        }
        return steambeatSitemapModuleLink;
    }

    public void setSteambeatSitemapModuleLink(final SteambeatSitemapModuleLink steambeatSitemapModuleLink) {
        this.steambeatSitemapModuleLink = steambeatSitemapModuleLink;
    }

    public KeywordService getKeywordService() {
        return keywordService;
    }

    private SteambeatSitemapModuleLink steambeatSitemapModuleLink;
    private KeywordService keywordService;
}
