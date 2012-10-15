package com.steambeat.domain.illustration;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.bingsearch.BingLink;
import com.steambeat.domain.eventbus.DomainEventBus;
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
        final List<Illustration> illustrations = Repositories.illustrations().forReferenceId(referenceId);
        if (illustrations.isEmpty()) {
            addIllustration(conceptIllustrationRequestEvent, referenceId);
        }
        sessionProvider.stop();
    }

    private void addIllustration(final ConceptIllustrationRequestEvent conceptIllustrationRequestEvent, final UUID referenceId) {
        final String link = bingLink.getIllustration(conceptIllustrationRequestEvent.getValue());
        final Illustration illustration = new Illustration(referenceId, link);
        Repositories.illustrations().add(illustration);
    }

    private BingLink bingLink;
    private SessionProvider sessionProvider;
}
