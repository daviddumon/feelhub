package com.steambeat.domain.illustration;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.DomainException;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.scraper.Scraper;
import com.steambeat.repositories.*;

import java.util.*;

public class UriIllustrationFactory {

    @Inject
    public UriIllustrationFactory(final Scraper scraper, final SessionProvider sessionProvider) {
        this.scraper = scraper;
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final UriIllustrationRequestEvent uriIllustrationRequestEvent) {
        sessionProvider.start();
        final UUID referenceId = uriIllustrationRequestEvent.getReferenceId();
        addAnIllustration(referenceId);
        sessionProvider.stop();
    }

    private void addAnIllustration(final UUID referenceId) {
        final Keyword keyword = getKeywordFor(referenceId);
        scraper.scrap(keyword.getValue());
        final Illustration illustration = new Illustration(referenceId, scraper.getIllustration());
        Repositories.illustrations().add(illustration);
    }

    protected Keyword getKeywordFor(final UUID referenceId) {
        final List<Keyword> keywords = Repositories.keywords().forReferenceId(referenceId);
        if (keywords != null) {
            return keywords.get(0);
        } else {
            throw new DomainException("the fuck just happens ????");
        }
    }

    private final Scraper scraper;
    private final SessionProvider sessionProvider;
}
