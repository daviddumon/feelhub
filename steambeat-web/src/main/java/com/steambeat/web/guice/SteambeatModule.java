package com.steambeat.web.guice;

import com.google.inject.*;
import com.steambeat.application.StatisticsService;
import com.steambeat.domain.alchemy.*;
import com.steambeat.domain.relation.alchemy.*;
import com.steambeat.domain.scrapers.UriScraper;
import com.steambeat.repositories.SessionProvider;

public class SteambeatModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(StatisticsService.class).toInstance(new StatisticsService());
        bind(UriScraper.class).toInstance(new UriScraper());
        bind(AlchemyEntityProvider.class).toInstance(new AlchemyJsonEntityProvider(new AlchemyLink()));
    }

    @Provides
    @Singleton
    public SessionProvider sessionProvider() {
        final SessionProvider sessionProvider = new SessionProvider();
        sessionProvider.init();
        return sessionProvider;
    }
}
