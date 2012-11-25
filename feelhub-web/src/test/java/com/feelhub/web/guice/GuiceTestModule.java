package com.feelhub.web.guice;

import com.feelhub.domain.alchemy.AlchemyLink;
import com.feelhub.domain.alchemy.FakeJsonAlchemyLink;
import com.feelhub.domain.alchemy.NamedEntityFactory;
import com.feelhub.domain.alchemy.NamedEntityProvider;
import com.feelhub.domain.bingsearch.BingLink;
import com.feelhub.domain.bingsearch.FakeBingLink;
import com.feelhub.domain.scraper.FakeScraper;
import com.feelhub.domain.scraper.Scraper;
import com.feelhub.domain.tag.uri.FakeUriResolver;
import com.feelhub.domain.tag.uri.UriResolver;
import com.feelhub.domain.translation.FakeTranslator;
import com.feelhub.domain.translation.Translator;
import com.feelhub.repositories.SessionProvider;
import com.feelhub.repositories.fakeRepositories.FakeSessionProvider;
import com.feelhub.web.filter.FakeOpenSessionInViewFilter;
import com.feelhub.web.filter.OpenSessionInViewFilter;
import com.feelhub.web.migration.FakeMigrationFilter;
import com.feelhub.web.migration.FakeMigrationRunner;
import com.feelhub.web.migration.MigrationRunner;
import com.feelhub.web.migration.web.MigrationFilter;
import com.feelhub.web.search.FeelingSearch;
import com.feelhub.web.search.IllustrationSearch;
import com.feelhub.web.search.RelationSearch;
import com.feelhub.web.search.StatisticsSearch;
import com.feelhub.web.search.fake.FakeFeelingSearch;
import com.feelhub.web.search.fake.FakeIllustrationSearch;
import com.feelhub.web.search.fake.FakeRelationSearch;
import com.feelhub.web.search.fake.FakeStatisticsSearch;
import com.feelhub.web.tools.FeelhubSitemapModuleLink;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

import java.io.IOException;
import java.util.Properties;

public class GuiceTestModule extends AbstractModule {

    @Override
    protected void configure() {
        Names.bindProperties(binder(), properties());
        bind(OpenSessionInViewFilter.class).to(FakeOpenSessionInViewFilter.class);
        bind(SessionProvider.class).to(FakeSessionProvider.class);
        bind(FeelingSearch.class).to(FakeFeelingSearch.class);
        bind(StatisticsSearch.class).to(FakeStatisticsSearch.class);
        bind(RelationSearch.class).to(FakeRelationSearch.class);
        bind(IllustrationSearch.class).to(FakeIllustrationSearch.class);
        bind(UriResolver.class).to(FakeUriResolver.class);
        bind(MigrationRunner.class).to(FakeMigrationRunner.class);
        bind(MigrationFilter.class).to(FakeMigrationFilter.class);
        bind(Scraper.class).to(FakeScraper.class);
        bind(NamedEntityProvider.class).toInstance(new NamedEntityProvider(new FakeJsonAlchemyLink(), new NamedEntityFactory()));
        bind(Translator.class).to(FakeTranslator.class);
        bind(AlchemyLink.class).to(FakeJsonAlchemyLink.class);
        bind(BingLink.class).to(FakeBingLink.class);
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
    public FeelhubSitemapModuleLink getFeelhubSitemapModuleLink() {
        if (feelhubSitemapModuleLink == null) {
            return new FeelhubSitemapModuleLink();
        }
        return feelhubSitemapModuleLink;
    }

    public void setFeelhubSitemapModuleLink(final FeelhubSitemapModuleLink feelhubSitemapModuleLink) {
        this.feelhubSitemapModuleLink = feelhubSitemapModuleLink;
    }

    private FeelhubSitemapModuleLink feelhubSitemapModuleLink;
}
