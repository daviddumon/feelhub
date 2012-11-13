package com.feelhub.domain.illustration;

import com.feelhub.domain.bingsearch.BingLink;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.repositories.*;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import java.util.*;

public class WordIllustrationFactory {

    @Inject
    public WordIllustrationFactory(final BingLink bingLink, final SessionProvider sessionProvider) {
        this.bingLink = bingLink;
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final WordIllustrationRequestEvent wordIllustrationRequestEvent) {
        sessionProvider.start();
        final UUID topicId = wordIllustrationRequestEvent.getTopicId();
        final List<Illustration> illustrations = Repositories.illustrations().forTopicId(topicId);
        if (illustrations.isEmpty()) {
            addIllustration(wordIllustrationRequestEvent, topicId);
        }
        sessionProvider.stop();
    }

    private void addIllustration(final WordIllustrationRequestEvent wordIllustrationRequestEvent, final UUID topicId) {
        final String link = bingLink.getIllustration(wordIllustrationRequestEvent.getValue());
        final Illustration illustration = new Illustration(topicId, link);
        Repositories.illustrations().add(illustration);
    }

    private final BingLink bingLink;
    private final SessionProvider sessionProvider;
}
