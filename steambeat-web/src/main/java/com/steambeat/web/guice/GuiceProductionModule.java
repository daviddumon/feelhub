package com.steambeat.web.guice;

import com.google.inject.*;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.alchemy.*;
import com.steambeat.domain.eventbus.DeadEventCatcher;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.reference.ReferenceFactory;
import com.steambeat.domain.scrapers.UriScraper;
import com.steambeat.domain.statistics.StatisticsFactory;
import com.steambeat.domain.translation.*;
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
        bind(Translator.class).toInstance(new Translator(new MicrosoftTranslatorLink()));
        bind(KeywordListener.class).toInstance(new KeywordListener(new KeywordFactory()));
    }

    @Provides
    @Singleton
    public SessionProvider sessionProvider() {
        final SessionProvider sessionProvider = new SessionProvider();
        sessionProvider.init();
        return sessionProvider;
    }
}
