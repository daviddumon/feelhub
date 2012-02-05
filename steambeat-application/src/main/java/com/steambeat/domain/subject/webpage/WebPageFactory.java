package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.DomainEventBus;
import com.steambeat.domain.scrapers.WebPageScraper;
import com.steambeat.repositories.Repositories;

public class WebPageFactory {

    public WebPage newWebPage(final Association association) {
        checkNotExists(association);
        final WebPageScraper webPageScraper = new WebPageScraper();
        webPageScraper.scrap(new Uri(association.getCanonicalUri()));
        final WebPage webPage = new WebPage(association, webPageScraper);
        DomainEventBus.INSTANCE.spread(new WebPageCreatedEvent(webPage));
        return webPage;
    }

    protected void checkNotExists(final Association association) {
        if (Repositories.webPages().get(association.getCanonicalUri()) != null) {
            throw new WebPageAlreadyExistsException(association.getCanonicalUri());
        }
    }
}
