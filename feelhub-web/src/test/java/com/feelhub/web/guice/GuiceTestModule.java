package com.feelhub.web.guice;

import com.feelhub.application.command.CommandBus;
import com.feelhub.application.mail.MailSender;
import com.feelhub.domain.alchemy.*;
import com.feelhub.domain.bing.*;
import com.feelhub.domain.cloudinary.*;
import com.feelhub.domain.scraper.*;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.domain.translation.*;
import com.feelhub.repositories.*;
import com.feelhub.repositories.fakeRepositories.FakeRepositories;
import com.feelhub.web.filter.*;
import com.feelhub.web.mail.FakeMailSender;
import com.feelhub.web.search.*;
import com.feelhub.web.search.fake.*;
import com.feelhub.web.social.FacebookConnector;
import com.feelhub.web.tools.FeelhubSitemapModuleLink;
import com.google.inject.*;
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
        bind(RelatedSearch.class).to(FakeRelatedSearch.class);
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

    @Provides
    public FacebookConnector getFacebookConnector() {
        final FacebookConnector facebookConnector = mock(FacebookConnector.class);
        when(facebookConnector.getUrl()).thenReturn("localhost");
        return facebookConnector;
    }

    public void setFeelhubSitemapModuleLink(final FeelhubSitemapModuleLink feelhubSitemapModuleLink) {
        this.feelhubSitemapModuleLink = feelhubSitemapModuleLink;
    }

    private FeelhubSitemapModuleLink feelhubSitemapModuleLink;
}
