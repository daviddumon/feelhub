package com.steambeat.domain.subject.webpage;

import com.google.inject.Inject;
import com.steambeat.domain.DomainEventBus;
import com.steambeat.domain.association.Association;
import com.steambeat.domain.scrapers.UriScraper;
import com.steambeat.repositories.Repositories;

public class WebPageFactory {

    @Inject
    public WebPageFactory(final UriScraper uriScraper) {
        this.uriScraper = uriScraper;
    }

    public WebPage newWebPage(final Association association) {
        if (checkIfExists(association)) {
            throw new WebPageAlreadyExistsException(association.getIdentifier());
        }
        return doCreateWebPage(association);
    }

    protected boolean checkIfExists(final Association association) {
        final WebPage webPage = (WebPage) Repositories.subjects().get(association.getSubjectId());
        return webPage != null;
    }

    private WebPage doCreateWebPage(final Association association) {
        final WebPage webPage = new WebPage(association);
        webPage.setScraper(uriScraper);
        //DomainEventBus.INSTANCE.spread(new WebPageCreatedEvent(webPage));
        DomainEventBus.INSTANCE.post(new WebPageCreatedEvent(webPage));
        return webPage;
    }

    private final UriScraper uriScraper;
}
