package com.feelhub.web.guice;

import com.feelhub.application.ActivationService;
import com.feelhub.application.FeelingService;
import com.feelhub.domain.admin.AdminApiCallsService;
import com.feelhub.domain.eventbus.DeadEventCatcher;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.scraper.HttpTopicAnalyzer;
import com.feelhub.domain.statistics.StatisticsFactory;
import com.feelhub.domain.topic.world.WorldListener;
import com.feelhub.domain.translation.Translator;
import com.feelhub.repositories.SessionProvider;
import com.feelhub.tools.MongoLinkAwareExecutor;
import com.feelhub.web.mail.MailBuilder;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
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
        //bind(MailWatcher.class).asEagerSingleton();
        bind(EventBus.class).to(AsyncEventBus.class).asEagerSingleton();
        bind(MailBuilder.class).asEagerSingleton();
        bind(DeadEventCatcher.class).asEagerSingleton();
        bind(FeelingService.class).asEagerSingleton();
        bind(StatisticsFactory.class).asEagerSingleton();
        bind(WorldListener.class).asEagerSingleton();
        bind(ActivationService.class).asEagerSingleton();
        bind(Translator.class).asEagerSingleton();
        bind(AdminApiCallsService.class).asEagerSingleton();
        bind(HttpTopicAnalyzer.class).asEagerSingleton();
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

    @Provides
    public AsyncEventBus eventBus(final MongoLinkAwareExecutor executor) {
        final AsyncEventBus asyncEventBus = new AsyncEventBus(executor);
        DomainEventBus.INSTANCE.setEventBus(asyncEventBus);
        return asyncEventBus;
    }
}
