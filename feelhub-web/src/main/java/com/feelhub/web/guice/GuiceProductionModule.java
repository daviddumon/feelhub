package com.feelhub.web.guice;

import com.feelhub.application.command.CommandBus;
import com.feelhub.domain.admin.AdminApiCallsService;
import com.feelhub.domain.eventbus.DeadEventCatcher;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.eventbus.HybridEventBus;
import com.feelhub.domain.statistics.StatisticsFactory;
import com.feelhub.domain.translation.Translator;
import com.feelhub.repositories.MongoRepositories;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.SessionProvider;
import com.feelhub.tools.FeelhubApplicationProperties;
import com.feelhub.tools.MongoLinkAwareExecutor;
import com.feelhub.web.mail.MailBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.mongodb.DB;
import org.jongo.Jongo;
import org.mongolink.Settings;

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

    @Provides
    @Singleton
    public DB dbProvider() {
        FeelhubApplicationProperties props = new FeelhubApplicationProperties();
        Settings dbSettings = props.getDbSettings();
        return dbSettings.createDbFactory().get(dbSettings.getDbName());
    }

    @Provides
    @Singleton
    public Jongo jongoProvider() {
        FeelhubApplicationProperties props = new FeelhubApplicationProperties();
        Settings dbSettings = props.getDbSettings();
        return new Jongo(dbSettings.createDbFactory().get(dbSettings.getDbName()));
    }
}
