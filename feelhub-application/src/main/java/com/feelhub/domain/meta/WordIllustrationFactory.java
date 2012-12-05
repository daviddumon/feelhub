package com.feelhub.domain.meta;

import com.feelhub.domain.bingsearch.BingLink;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.repositories.SessionProvider;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import java.util.UUID;

public class WordIllustrationFactory {

    @Inject
    public WordIllustrationFactory(final BingLink bingLink, final SessionProvider sessionProvider) {
        this.bingLink = bingLink;
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final WordIllustrationRequestEvent wordIllustrationRequestEvent) {
        //sessionProvider.start();
        //final UUID topicId = wordIllustrationRequestEvent.getTopicId();
        //final List<Illustration> illustrations = Repositories.illustrations().forTopicId(topicId);
        //if (illustrations.isEmpty()) {
        //    addIllustrations(wordIllustrationRequestEvent, topicId);
        //}
        //sessionProvider.stop();
    }

    private void addIllustrations(final WordIllustrationRequestEvent wordIllustrationRequestEvent, final UUID topicId) {
        //final List<String> links = bingLink.getIllustrations(wordIllustrationRequestEvent.getValue(), wordIllustrationRequestEvent.getType());
        //for (String link : links) {
        //    final Illustration illustration = new Illustration(topicId, link);
        //    Repositories.illustrations().add(illustration);
        //}
    }

    private final BingLink bingLink;
    private final SessionProvider sessionProvider;
}
