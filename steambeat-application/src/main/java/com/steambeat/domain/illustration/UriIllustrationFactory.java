package com.steambeat.domain.illustration;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
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
        final List<Illustration> illustrations = Repositories.illustrations().forReferenceId(referenceId);
        if (illustrations.isEmpty()) {
            addAnIllustration(uriIllustrationRequestEvent);
        }
        sessionProvider.stop();
    }

    private void addAnIllustration(final UriIllustrationRequestEvent uriIllustrationRequestEvent) {
        scraper.scrap(uriIllustrationRequestEvent.getValue());
        final Illustration illustration = new Illustration(uriIllustrationRequestEvent.getReferenceId(), scraper.getIllustration());
        Repositories.illustrations().add(illustration);
    }

    private final Scraper scraper;
    private final SessionProvider sessionProvider;
}
