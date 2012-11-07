package com.feelhub.web.guice;

import com.feelhub.application.*;
import com.feelhub.domain.alchemy.*;
import com.feelhub.domain.bingsearch.*;
import com.feelhub.domain.keyword.KeywordFactory;
import com.feelhub.domain.reference.ReferenceFactory;
import com.feelhub.domain.scraper.*;
import com.feelhub.domain.translation.*;
import com.feelhub.domain.uri.*;
import com.feelhub.repositories.SessionProvider;
import com.feelhub.repositories.fakeRepositories.FakeSessionProvider;
import com.feelhub.web.filter.*;
import com.feelhub.web.mail.*;
import com.feelhub.web.migration.*;
import com.feelhub.web.migration.web.MigrationFilter;
import com.feelhub.web.search.*;
import com.feelhub.web.search.fake.*;
import com.feelhub.web.tools.FeelhubSitemapModuleLink;
import com.google.inject.*;
import com.google.inject.name.Names;

import java.io.IOException;
import java.util.Properties;

import static org.mockito.Mockito.*;

public class GuiceTestModule extends AbstractModule {

    @Override
    protected void configure() {
        Names.bindProperties(binder(), properties());
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
        bind(MailSender.class).to(FakeMailSender.class);
    }

    private Properties properties() {
        try {
            final Properties properties = new Properties();
            properties.load(getClass().getResourceAsStream("/feelhub-web.properties"));
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties", e);
        }
    }

    @Provides
    public KeywordService keywordService() {
        keywordService = spy(new KeywordService(new ReferenceService(new ReferenceFactory()), new KeywordFactory(), new FakeTranslator(), new UriManager(new FakeUriResolver())));
        return keywordService;
    }

    @Provides
    public FeelhubSitemapModuleLink getFeelhubSitemapModuleLink() {
        if (feelhubSitemapModuleLink == null) {
            return new FeelhubSitemapModuleLink();
        }
        return feelhubSitemapModuleLink;
    }

    public void setFeelhubSitemapModuleLink(final FeelhubSitemapModuleLink feelhubSitemapModuleLink) {
        this.feelhubSitemapModuleLink = feelhubSitemapModuleLink;
    }

    public KeywordService getKeywordService() {
        return keywordService;
    }

    private FeelhubSitemapModuleLink feelhubSitemapModuleLink;
    private KeywordService keywordService;
}
