package com.steambeat.domain.illustration;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.bingsearch.BingLink;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.reference.ConceptReferencesChangedEvent;
import com.steambeat.repositories.*;

import java.util.List;

public class ConceptIllustrationManager extends IllustrationManager {

    @Inject
    public ConceptIllustrationManager(final BingLink bingLink, final SessionProvider sessionProvider) {
        this.bingLink = bingLink;
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final ConceptReferencesChangedEvent event) {
        sessionProvider.start();
        final List<Illustration> illustrations = getAllIllustrations(event);
        if (illustrations.isEmpty()) {
            addAnIllustration(event);
        } else {
            migrateExistingIllustrations(illustrations, event.getNewReferenceId());
            removeDuplicate(illustrations);
        }
        sessionProvider.stop();
    }

    private void addAnIllustration(final ConceptReferencesChangedEvent eventConcept) {
        final String link = getLink(eventConcept);
        final Illustration illustration = new Illustration(eventConcept.getNewReferenceId(), link);
        Repositories.illustrations().add(illustration);
    }

    private String getLink(final ConceptReferencesChangedEvent eventConcept) {
        final Keyword keyword = getKeywordFor(eventConcept);
        return bingLink.getIllustration(keyword);
    }

    private final BingLink bingLink;
    private final SessionProvider sessionProvider;
}
