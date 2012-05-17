package com.steambeat.web.guice;

import com.google.inject.*;
import com.steambeat.application.*;
import com.steambeat.domain.alchemy.*;
import com.steambeat.domain.association.uri.UriPathResolver;
import com.steambeat.domain.scrapers.UriScraper;
import com.steambeat.domain.translation.MicrosoftTranslator;
import com.steambeat.repositories.SessionProvider;

public class SteambeatModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(StatisticsService.class).toInstance(new StatisticsService());
        bind(UriScraper.class).toInstance(new UriScraper());
        bind(NamedEntityProvider.class).toInstance(new NamedEntityJsonProvider(new AlchemyLink(), new NamedEntityBuilder(new AssociationService(new UriPathResolver(), new MicrosoftTranslator()))));
    }

    @Provides
    @Singleton
    public SessionProvider sessionProvider() {
        final SessionProvider sessionProvider = new SessionProvider();
        sessionProvider.init();
        return sessionProvider;
    }
}
