package com.steambeat.web.guice;

import com.google.inject.*;
import com.steambeat.domain.alchemy.*;
import com.steambeat.domain.uri.UriPathResolver;
import com.steambeat.domain.bingsearch.BingLink;
import com.steambeat.domain.eventbus.DeadEventCatcher;
import com.steambeat.domain.scrapers.UriScraper;
import com.steambeat.domain.statistics.StatisticsFactory;
import com.steambeat.domain.subject.concept.ConceptFactory;
import com.steambeat.domain.translation.MicrosoftTranslator;
import com.steambeat.repositories.SessionProvider;
import com.steambeat.web.mail.*;

public class ProductionGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(StatisticsFactory.class).toInstance(new StatisticsFactory());
        bind(AlchemyEntityAnalyzer.class).toInstance(new AlchemyEntityAnalyzer(new NamedEntityJsonProvider(new AlchemyLink(), new NamedEntityBuilder(new AssociationService(new UriPathResolver(), new MicrosoftTranslator()))), new AssociationService(new UriPathResolver(), new MicrosoftTranslator()), new ConceptFactory(new BingLink())));
        bind(UriScraper.class).toInstance(new UriScraper());
        bind(NamedEntityProvider.class).toInstance(new NamedEntityJsonProvider(new AlchemyLink(), new NamedEntityBuilder(new AssociationService(new UriPathResolver(), new MicrosoftTranslator()))));
        bind(MailBuilder.class).toInstance(new MailBuilder(new MailSender()));
        bind(DeadEventCatcher.class).toInstance(new DeadEventCatcher());
    }

    @Provides
    @Singleton
    public SessionProvider sessionProvider() {
        final SessionProvider sessionProvider = new SessionProvider();
        sessionProvider.init();
        return sessionProvider;
    }
}
