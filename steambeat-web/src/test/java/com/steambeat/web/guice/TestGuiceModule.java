package com.steambeat.web.guice;

import com.google.inject.*;
import com.steambeat.application.*;
import com.steambeat.domain.alchemy.*;
import com.steambeat.domain.uri.UriPathResolver;
import com.steambeat.domain.bingsearch.BingLink;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.scrapers.UriScraper;
import com.steambeat.domain.thesaurus.Language;
import com.steambeat.domain.translation.MicrosoftTranslator;
import com.steambeat.repositories.SessionProvider;
import com.steambeat.test.*;
import com.steambeat.test.fakeRepositories.FakeSessionProvider;
import com.steambeat.web.filter.*;
import com.steambeat.web.mail.*;
import com.steambeat.web.migration.*;
import com.steambeat.web.migration.web.MigrationFilter;
import com.steambeat.web.search.*;
import com.steambeat.web.search.fake.*;
import com.steambeat.web.tools.SteambeatSitemapModuleLink;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class TestGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(OpenSessionInViewFilter.class).to(FakeOpenSessionInViewFilter.class);
        bind(IdentityFilter.class).to(FakeIdentityFilter.class);
        bind(SessionProvider.class).to(FakeSessionProvider.class);
        bind(OpinionSearch.class).to(FakeOpinionSearch.class);
        bind(StatisticsSearch.class).to(FakeStatisticsSearch.class);
        bind(RelationSearch.class).to(FakeRelationSearch.class);
        bind(UriPathResolver.class).to(FakeUriPathResolver.class);
        bind(MigrationRunner.class).to(FakeMigrationRunner.class);
        bind(MigrationFilter.class).to(FakeMigrationFilter.class);
        bind(UriScraper.class).to(FakeUriScraper.class);
        bind(NamedEntityProvider.class).toInstance(new NamedEntityJsonProvider(new FakeJsonAlchemyLink(), new NamedEntityBuilder(new AssociationService(new FakeUriPathResolver(), new FakeMicrosoftTranslator()))));
        bind(MicrosoftTranslator.class).to(FakeMicrosoftTranslator.class);
        bind(AlchemyLink.class).to(FakeJsonAlchemyLink.class);
        bind(BingLink.class).to(FakeBingLink.class);
        bind(MailBuilder.class).toInstance(new MailBuilder(new FakeMailSender()));
    }

    @Provides
    public KeywordService keywordService() {
        keywordService = mock(KeywordService.class);
        final Language en = Language.forString("en");
        when(keywordService.lookUp("cool", en)).thenReturn(new Keyword("cool", en, UUID.randomUUID()));
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
