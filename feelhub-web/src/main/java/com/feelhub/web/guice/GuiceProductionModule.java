package com.feelhub.web.guice;

import com.feelhub.application.OpinionService;
import com.feelhub.domain.alchemy.AlchemyAnalyzer;
import com.feelhub.domain.eventbus.DeadEventCatcher;
import com.feelhub.domain.illustration.*;
import com.feelhub.domain.statistics.StatisticsFactory;
import com.feelhub.domain.steam.SteamListener;
import com.feelhub.repositories.SessionProvider;
import com.feelhub.web.mail.MailBuilder;
import com.feelhub.web.tools.FeelhubWebProperties;
import com.google.inject.*;
import com.google.inject.name.Names;

import java.util.Properties;

public class GuiceProductionModule extends AbstractModule {

    @Override
    protected void configure() {
        Names.bindProperties(binder(), properties());
        bind(MailBuilder.class).asEagerSingleton();
        bind(DeadEventCatcher.class).asEagerSingleton();
        bind(OpinionService.class).asEagerSingleton();
        bind(StatisticsFactory.class).asEagerSingleton();
        bind(SteamListener.class).asEagerSingleton();
        bind(ConceptIllustrationFactory.class).asEagerSingleton();
        bind(UriIllustrationFactory.class).asEagerSingleton();
        bind(AlchemyAnalyzer.class).asEagerSingleton();
    }

    private Properties properties() {
        return new FeelhubWebProperties().getProperties();
    }

    @Provides
    @Singleton
    public SessionProvider sessionProvider() {
        final SessionProvider sessionProvider = new SessionProvider();
        sessionProvider.init();
        return sessionProvider;
    }
}
