package com.feelhub.web.guice;

import com.feelhub.application.ActivationService;
import com.feelhub.application.FeelingService;
import com.feelhub.domain.alchemy.AlchemyAnalyzer;
import com.feelhub.domain.eventbus.DeadEventCatcher;
import com.feelhub.domain.meta.UriMetaInformationFactory;
import com.feelhub.domain.meta.WordIllustrationFactory;
import com.feelhub.domain.statistics.StatisticsFactory;
import com.feelhub.domain.world.WorldListener;
import com.feelhub.repositories.SessionProvider;
import com.feelhub.web.mail.MailWatcher;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import java.io.IOException;
import java.util.Properties;

public class GuiceProductionModule extends AbstractModule {

    @Override
    protected void configure() {
        Names.bindProperties(binder(), properties());
        bind(MailWatcher.class).asEagerSingleton();
        bind(DeadEventCatcher.class).asEagerSingleton();
        bind(FeelingService.class).asEagerSingleton();
        bind(StatisticsFactory.class).asEagerSingleton();
        bind(WorldListener.class).asEagerSingleton();
        bind(WordIllustrationFactory.class).asEagerSingleton();
        bind(UriMetaInformationFactory.class).asEagerSingleton();
        bind(AlchemyAnalyzer.class).asEagerSingleton();
        bind(ActivationService.class).asEagerSingleton();
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
    @Singleton
    public SessionProvider sessionProvider() {
        final SessionProvider sessionProvider = new SessionProvider();
        sessionProvider.init();
        return sessionProvider;
    }
}
