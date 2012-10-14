package com.steambeat.web.guice;

import com.google.inject.*;
import com.steambeat.domain.alchemy.AlchemyAnalyzer;
import com.steambeat.domain.eventbus.DeadEventCatcher;
import com.steambeat.domain.illustration.*;
import com.steambeat.domain.relation.*;
import com.steambeat.domain.statistics.StatisticsFactory;
import com.steambeat.domain.uri.UriManager;
import com.steambeat.repositories.SessionProvider;
import com.steambeat.web.mail.MailBuilder;

public class GuiceProductionModule extends AbstractModule {

    @Override
    protected void configure() {
        //bind(SubjectIdentifier.class).asEagerSingleton();
        //bind(ConceptTranslator.class).asEagerSingleton();
        //bind(ConceptReferenceManager.class).asEagerSingleton();
        //bind(KeywordManager.class).asEagerSingleton();
        //bind(OpinionManager.class).asEagerSingleton();
        //bind(RelationManager.class).asEagerSingleton();
        //bind(StatisticsManager.class).asEagerSingleton();
        //bind(ConceptIllustrationManager.class).asEagerSingleton();

        bind(UriManager.class).asEagerSingleton();
        //bind(UriReferenceManager.class).asEagerSingleton();
        bind(AlchemyAnalyzer.class).asEagerSingleton();
        //bind(UriIllustrationManager.class).asEagerSingleton();
        //bind(ConceptGroupTranslator.class).asEagerSingleton();
        //bind(ConceptGroupReferenceManager.class).asEagerSingleton();
        bind(AlchemyRelationBinder.class).asEagerSingleton();
        bind(OpinionRelationBinder.class).asEagerSingleton();

        bind(MailBuilder.class).asEagerSingleton();
        bind(DeadEventCatcher.class).asEagerSingleton();
        bind(UriIllustrationFactory.class).asEagerSingleton();
        bind(ConceptIllustrationFactory.class).asEagerSingleton();
        bind(StatisticsFactory.class).asEagerSingleton();
    }

    @Provides
    @Singleton
    public SessionProvider sessionProvider() {
        final SessionProvider sessionProvider = new SessionProvider();
        sessionProvider.init();
        return sessionProvider;
    }
}
