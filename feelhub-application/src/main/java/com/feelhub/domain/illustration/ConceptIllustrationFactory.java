package com.feelhub.domain.illustration;

import com.feelhub.domain.bingsearch.BingLink;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.repositories.*;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

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
        final UUID topicId = conceptIllustrationRequestEvent.getTopicId();
        final List<Illustration> illustrations = Repositories.illustrations().forTopicId(topicId);
        if (illustrations.isEmpty()) {
            addIllustration(conceptIllustrationRequestEvent, topicId);
        }
        sessionProvider.stop();
    }

    private void addIllustration(final ConceptIllustrationRequestEvent conceptIllustrationRequestEvent, final UUID topicId) {
        final String link = bingLink.getIllustration(conceptIllustrationRequestEvent.getValue());
        final Illustration illustration = new Illustration(topicId, link);
        Repositories.illustrations().add(illustration);
    }

    private final BingLink bingLink;
    private final SessionProvider sessionProvider;
}
