package com.steambeat.web.guice;

import com.google.inject.*;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.SubjectIdentifier;
import com.steambeat.domain.alchemy.*;
import com.steambeat.domain.bingsearch.BingLink;
import com.steambeat.domain.concept.ConceptTranslator;
import com.steambeat.domain.eventbus.DeadEventCatcher;
import com.steambeat.domain.illustration.IllustrationManager;
import com.steambeat.domain.keyword.KeywordFactory;
import com.steambeat.domain.reference.*;
import com.steambeat.domain.scrapers.UriScraper;
import com.steambeat.domain.statistics.StatisticsFactory;
import com.steambeat.repositories.SessionProvider;
import com.steambeat.web.mail.*;

public class GuiceProductionModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(StatisticsFactory.class).toInstance(new StatisticsFactory());
        bind(AlchemyEntityAnalyzer.class).toInstance(new AlchemyEntityAnalyzer(new NamedEntityJsonProvider(new AlchemyLink(), new NamedEntityBuilder(new KeywordService(new KeywordFactory(), new ReferenceFactory()))), new KeywordService(new KeywordFactory(), new ReferenceFactory())));
        bind(UriScraper.class).toInstance(new UriScraper());
        bind(NamedEntityProvider.class).toInstance(new NamedEntityJsonProvider(new AlchemyLink(), new NamedEntityBuilder(new KeywordService(new KeywordFactory(), new ReferenceFactory()))));
        bind(MailBuilder.class).toInstance(new MailBuilder(new MailSender()));
        bind(DeadEventCatcher.class).toInstance(new DeadEventCatcher());


        bind(SubjectIdentifier.class).toInstance(new SubjectIdentifier());
        bind(ConceptTranslator.class).toInstance(new ConceptTranslator(new KeywordService(new KeywordFactory(), new ReferenceFactory())));
        bind(ReferenceManager.class).toInstance(new ReferenceManager());
        bind(IllustrationManager.class).toInstance(new IllustrationManager(new BingLink()));
    }

    @Provides
    @Singleton
    public SessionProvider sessionProvider() {
        final SessionProvider sessionProvider = new SessionProvider();
        sessionProvider.init();
        return sessionProvider;
    }
}
