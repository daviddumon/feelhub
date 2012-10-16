package com.steambeat.web.guice;

import com.google.inject.*;
import com.steambeat.application.OpinionService;
import com.steambeat.domain.alchemy.AlchemyAnalyzer;
import com.steambeat.domain.eventbus.DeadEventCatcher;
import com.steambeat.domain.illustration.*;
import com.steambeat.domain.statistics.StatisticsFactory;
import com.steambeat.domain.steam.SteamListener;
import com.steambeat.repositories.SessionProvider;
import com.steambeat.web.mail.MailBuilder;

public class GuiceProductionModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MailBuilder.class).asEagerSingleton();
        bind(DeadEventCatcher.class).asEagerSingleton();
        bind(OpinionService.class).asEagerSingleton();
        bind(StatisticsFactory.class).asEagerSingleton();
        bind(SteamListener.class).asEagerSingleton();
        bind(ConceptIllustrationFactory.class).asEagerSingleton();
        bind(UriIllustrationFactory.class).asEagerSingleton();
        bind(AlchemyAnalyzer.class).asEagerSingleton();
    }

    @Provides
    @Singleton
    public SessionProvider sessionProvider() {
        final SessionProvider sessionProvider = new SessionProvider();
        sessionProvider.init();
        return sessionProvider;
    }
}
