package com.steambeat.domain.illustration;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.reference.ConceptReferencesChangedEvent;
import com.steambeat.repositories.SessionProvider;

import java.util.List;

public class ConceptIllustrationManager extends IllustrationManager {

    @Inject
    public ConceptIllustrationManager(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final ConceptReferencesChangedEvent event) {
        sessionProvider.start();
        final List<Illustration> illustrations = getAllIllustrations(event);
        if (!illustrations.isEmpty()) {
            migrateExistingIllustrations(illustrations, event.getNewReferenceId());
            removeDuplicate(illustrations);
        } else {
            final ConceptIllustrationRequestEvent conceptIllustrationRequestEvent = new ConceptIllustrationRequestEvent(event.getNewReferenceId());
            DomainEventBus.INSTANCE.post(conceptIllustrationRequestEvent);
        }
        sessionProvider.stop();
    }

    private final SessionProvider sessionProvider;
}
