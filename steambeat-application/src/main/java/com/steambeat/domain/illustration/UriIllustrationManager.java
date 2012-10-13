package com.steambeat.domain.illustration;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.reference.UriReferencesChangedEvent;
import com.steambeat.repositories.SessionProvider;

import java.util.List;

public class UriIllustrationManager extends IllustrationManager {

    @Inject
    public UriIllustrationManager(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final UriReferencesChangedEvent event) {
        sessionProvider.start();
        final List<Illustration> illustrations = getAllIllustrations(event);
        if (!illustrations.isEmpty()) {
            migrateExistingIllustrations(illustrations, event.getNewReferenceId());
            removeDuplicate(illustrations);
        } else {
            final UriIllustrationRequestEvent uriIllustrationRequestEvent = new UriIllustrationRequestEvent(event.getNewReferenceId());
            DomainEventBus.INSTANCE.post(uriIllustrationRequestEvent);
        }
        sessionProvider.stop();
    }

    private final SessionProvider sessionProvider;
}
