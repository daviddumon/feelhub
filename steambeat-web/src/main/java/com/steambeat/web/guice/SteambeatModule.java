package com.steambeat.web.guice;

import com.google.inject.*;
import com.steambeat.application.StatisticsService;
import com.steambeat.domain.subject.feed.CanonicalUriFinder;
import com.steambeat.repositories.SessionProvider;

public class SteambeatModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CanonicalUriFinder.class).in(Singleton.class);
        bind(StatisticsService.class).toInstance(new StatisticsService());
    }

    @Provides
    @Singleton
    public SessionProvider sessionProvider() {
        final SessionProvider result = new SessionProvider();
        result.init();
        return result;
    }
}
