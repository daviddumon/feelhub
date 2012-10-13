package com.steambeat.domain.illustration;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.DomainException;
import com.steambeat.domain.bingsearch.BingLink;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.repositories.*;

import java.util.*;

public class ConceptIllustrationFactory {

    @Inject
    public ConceptIllustrationFactory(final BingLink bingLink, final SessionProvider sessionProvider) {
        this.bingLink = bingLink;
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final ConceptIllustrationRequestEvent conceptIllustrationRequestEvent) {
        sessionProvider.start();
        final UUID referenceId = conceptIllustrationRequestEvent.getReferenceId();
        final String link = getLink(referenceId);
        final Illustration illustration = new Illustration(referenceId, link);
        Repositories.illustrations().add(illustration);
        sessionProvider.stop();
    }

    private String getLink(final UUID referenceId) {
        final Keyword keyword = getKeywordFor(referenceId);
        return bingLink.getIllustration(keyword);
    }

    private Keyword getKeywordFor(final UUID referenceId) {
        final List<Keyword> keywords = Repositories.keywords().forReferenceId(referenceId);
        if (keywords != null) {
            return keywords.get(0);
        } else {
            throw new DomainException("the fuck just happens ????");
        }
    }

    private BingLink bingLink;
    private SessionProvider sessionProvider;
}
