package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.DomainEventBus;
import com.steambeat.domain.scrapers.UriScraper;
import com.steambeat.repositories.Repositories;

public class WebPageFactory {

    public WebPage newWebPage(final Association association) {
        checkNotExists(association);
        final UriScraper uriScraper = new UriScraper();
        uriScraper.scrap(new Uri(association.getCanonicalUri()));
        final WebPage webPage = new WebPage(association, uriScraper);
        DomainEventBus.INSTANCE.spread(new WebPageCreatedEvent(webPage));
        return webPage;
    }

    protected void checkNotExists(final Association association) {
        if (Repositories.webPages().get(association.getCanonicalUri()) != null) {
            throw new WebPageAlreadyExistsException(association.getCanonicalUri());
        }
    }
}
