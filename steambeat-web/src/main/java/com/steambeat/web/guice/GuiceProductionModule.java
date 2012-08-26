package com.steambeat.web.guice;

import com.google.inject.*;
import com.steambeat.domain.SubjectIdentifier;
import com.steambeat.domain.alchemy.AlchemyAnalyzer;
import com.steambeat.domain.concept.*;
import com.steambeat.domain.eventbus.DeadEventCatcher;
import com.steambeat.domain.illustration.*;
import com.steambeat.domain.keyword.KeywordManager;
import com.steambeat.domain.opinion.OpinionManager;
import com.steambeat.domain.reference.*;
import com.steambeat.domain.relation.*;
import com.steambeat.domain.statistics.*;
import com.steambeat.domain.uri.UriManager;
import com.steambeat.repositories.SessionProvider;
import com.steambeat.web.mail.MailBuilder;

public class GuiceProductionModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SubjectIdentifier.class).asEagerSingleton();
        bind(ConceptTranslator.class).asEagerSingleton();
        bind(ConceptReferenceManager.class).asEagerSingleton();
        bind(ConceptIllustrationManager.class).asEagerSingleton();
        bind(DeadEventCatcher.class).asEagerSingleton();
        bind(MailBuilder.class).asEagerSingleton();
        bind(StatisticsFactory.class).asEagerSingleton();
        bind(KeywordManager.class).asEagerSingleton();
        bind(OpinionManager.class).asEagerSingleton();
        bind(RelationManager.class).asEagerSingleton();
        bind(StatisticsManager.class).asEagerSingleton();
        bind(UriManager.class).asEagerSingleton();
        bind(UriReferenceManager.class).asEagerSingleton();
        bind(AlchemyAnalyzer.class).asEagerSingleton();
        bind(UriIllustrationManager.class).asEagerSingleton();
        bind(ConceptGroupTranslator.class).asEagerSingleton();
        bind(ConceptGroupReferenceManager.class).asEagerSingleton();
        bind(RelationBinder.class).asEagerSingleton();
    }

    @Provides
    @Singleton
    public SessionProvider sessionProvider() {
        final SessionProvider sessionProvider = new SessionProvider();
        sessionProvider.init();
        return sessionProvider;
    }
}
