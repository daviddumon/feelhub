package com.feelhub.web.guice;

import com.feelhub.application.command.CommandBus;
import com.feelhub.domain.admin.AdminApiCallsService;
import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.statistics.StatisticsFactory;
import com.feelhub.domain.translation.Translator;
import com.feelhub.repositories.*;
import com.feelhub.tools.MongoLinkAwareExecutor;
import com.feelhub.web.mail.MailBuilder;
import com.google.inject.*;
import com.google.inject.name.Names;

import java.io.IOException;
import java.util.Properties;

public class GuiceProductionModule extends AbstractModule {

    @Override
    protected void configure() {
        Names.bindProperties(binder(), properties());
        //bind(MailWatcher.class).asEagerSingleton();
        bind(MailBuilder.class).in(Singleton.class);
        bind(DeadEventCatcher.class).in(Singleton.class);
        bind(StatisticsFactory.class).in(Singleton.class);
        bind(AdminApiCallsService.class).in(Singleton.class);
        bind(Translator.class).in(Singleton.class);
        bind(MongoLinkAwareExecutor.class).in(Singleton.class);
        bind(Repositories.class).to(MongoRepositories.class).in(Singleton.class);
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
    @Singleton
    public CommandBus commandBus(final SessionProvider provider) {
        return new CommandBus(provider);
    }

    @Provides
    @Singleton
    public HybridEventBus eventBus(final MongoLinkAwareExecutor executor) {
        final HybridEventBus eventBus = new HybridEventBus(executor);
        DomainEventBus.INSTANCE.setEventBus(eventBus);
        return eventBus;
    }
}
