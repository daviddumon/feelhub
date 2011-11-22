package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.DomainEventBus;
import com.steambeat.repositories.Repositories;

public class WebPageFactory {

    public WebPageFactory() {
    }

    public WebPage buildWebPage(final Association association) {
        checkNotExists(association);
        final WebPage result = new WebPage(association);
        DomainEventBus.INSTANCE.spread(new WebPageCreatedEvent(result));
        return result;
    }

    private void checkNotExists(final Association association) {
        if (Repositories.webPages().get(association.getCanonicalUri()) != null) {
            throw new WebPageAlreadyExistsException(association.getCanonicalUri());
        }
    }
}
