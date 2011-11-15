package com.steambeat.domain.subject.webpage;

import com.google.inject.Inject;
import com.steambeat.domain.DomainEventBus;
import com.steambeat.repositories.Repositories;
import com.steambeat.tools.HtmlParser;

public class WebPageFactory {

    @Inject
    public WebPageFactory(final HtmlParser parser) {
        this.parser = parser;
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

    private final HtmlParser parser;
}
