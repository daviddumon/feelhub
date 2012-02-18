package com.steambeat.web.guice;

import com.google.inject.*;
import com.steambeat.application.StatisticsService;
import com.steambeat.domain.analytics.identifiers.uri.UriPathResolver;
import com.steambeat.repositories.SessionProvider;

public class SteambeatModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UriPathResolver.class).in(Singleton.class);
        bind(StatisticsService.class).toInstance(new StatisticsService());
    }

    @Provides
    @Singleton
    public SessionProvider sessionProvider() {
        final SessionProvider sessionProvider = new SessionProvider();
        sessionProvider.init();
        return sessionProvider;
    }
}
