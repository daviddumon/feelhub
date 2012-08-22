package com.steambeat.web.guice;

import com.google.inject.*;
import com.steambeat.domain.SubjectIdentifier;
import com.steambeat.domain.concept.ConceptTranslator;
import com.steambeat.domain.eventbus.DeadEventCatcher;
import com.steambeat.domain.illustration.IllustrationManager;
import com.steambeat.domain.keyword.KeywordManager;
import com.steambeat.domain.opinion.OpinionManager;
import com.steambeat.domain.reference.ReferenceManager;
import com.steambeat.domain.relation.RelationManager;
import com.steambeat.domain.statistics.*;
import com.steambeat.repositories.SessionProvider;
import com.steambeat.web.mail.MailBuilder;

public class GuiceProductionModule extends AbstractModule {

    @Override
    protected void configure() {
        //bind(AlchemyEntityAnalyzer.class).asEagerSingleton();
        //bind(UriScraper.class).asEagerSingleton();
        //bind(NamedEntityProvider.class).asEagerSingleton();
        bind(MailBuilder.class).asEagerSingleton();
        bind(DeadEventCatcher.class).asEagerSingleton();
        bind(SubjectIdentifier.class).asEagerSingleton();
        bind(ConceptTranslator.class).asEagerSingleton();
        bind(ReferenceManager.class).asEagerSingleton();
        bind(IllustrationManager.class).asEagerSingleton();
        bind(StatisticsFactory.class).asEagerSingleton();
        bind(KeywordManager.class).asEagerSingleton();
        bind(OpinionManager.class).asEagerSingleton();
        bind(RelationManager.class).asEagerSingleton();
        bind(StatisticsManager.class).asEagerSingleton();
    }

    @Provides
    @Singleton
    public SessionProvider sessionProvider() {
        final SessionProvider sessionProvider = new SessionProvider();
        sessionProvider.init();
        return sessionProvider;
    }
}
