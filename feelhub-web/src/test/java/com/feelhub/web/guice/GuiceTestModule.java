package com.feelhub.web.guice;

import com.feelhub.application.command.CommandBus;
import com.feelhub.application.mail.MailSender;
import com.feelhub.domain.alchemy.AlchemyLink;
import com.feelhub.domain.alchemy.FakeAlchemyLink;
import com.feelhub.domain.alchemy.NamedEntityProvider;
import com.feelhub.domain.bing.BingLink;
import com.feelhub.domain.bing.FakeBingLink;
import com.feelhub.domain.cloudinary.CloudinaryLink;
import com.feelhub.domain.cloudinary.FakeCloudinaryLink;
import com.feelhub.domain.scraper.FakeScraper;
import com.feelhub.domain.scraper.Scraper;
import com.feelhub.domain.topic.http.uri.FakeUriResolver;
import com.feelhub.domain.topic.http.uri.UriResolver;
import com.feelhub.domain.translation.FakeTranslator;
import com.feelhub.domain.translation.Translator;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.SessionProvider;
import com.feelhub.repositories.fakeRepositories.FakeRepositories;
import com.feelhub.web.filter.FakeOpenSessionInViewFilter;
import com.feelhub.web.filter.OpenSessionInViewFilter;
import com.feelhub.web.mail.FakeMailSender;
import com.feelhub.web.search.FeelingSearch;
import com.feelhub.web.search.MediaSearch;
import com.feelhub.web.search.RelatedSearch;
import com.feelhub.web.search.StatisticsSearch;
import com.feelhub.web.search.fake.FakeFeelingSearch;
import com.feelhub.web.search.fake.FakeMediaSearch;
import com.feelhub.web.search.fake.FakeRelatedSearch;
import com.feelhub.web.search.fake.FakeStatisticsSearch;
import com.feelhub.web.tools.FeelhubSitemapModuleLink;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.mongodb.DB;

import java.io.IOException;
import java.util.Properties;

import static org.mockito.Mockito.*;

public class GuiceTestModule extends AbstractModule {

    @Override
    protected void configure() {
        Names.bindProperties(binder(), properties());
        bind(SessionProvider.class).to(DummySessionProvider.class);
        bind(FeelingSearch.class).to(FakeFeelingSearch.class);
        bind(StatisticsSearch.class).to(FakeStatisticsSearch.class);
        bind(RelatedSearch.class).to(FakeRelatedSearch.class);
        bind(MediaSearch.class).to(FakeMediaSearch.class);
        bind(UriResolver.class).to(FakeUriResolver.class);
        bind(Scraper.class).to(FakeScraper.class);
        bind(NamedEntityProvider.class).toInstance(new NamedEntityProvider(new FakeAlchemyLink()));
        bind(Translator.class).to(FakeTranslator.class);
        bind(AlchemyLink.class).to(FakeAlchemyLink.class);
        bind(BingLink.class).to(FakeBingLink.class);
        bind(MailSender.class).to(FakeMailSender.class);
        bind(CloudinaryLink.class).to(FakeCloudinaryLink.class);
        bind(CommandBus.class).in(Singleton.class);
        bind(Repositories.class).to(FakeRepositories.class);
        bind(OpenSessionInViewFilter.class).to(FakeOpenSessionInViewFilter.class);

        bind(DB.class).toInstance(mock(DB.class));
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
