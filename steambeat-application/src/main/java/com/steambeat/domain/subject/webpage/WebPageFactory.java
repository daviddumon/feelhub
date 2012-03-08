package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.DomainEventBus;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.scrapers.UriScraper;
import com.steambeat.repositories.Repositories;

public class WebPageFactory {

    public WebPage newWebPage(final Association association) {
        if (checkIfExists(association)) {
            throw new WebPageAlreadyExistsException(association.getId());
        }
        return doCreateWebPage(association);
    }

    protected boolean checkIfExists(final Association association) {
        final WebPage webPage = (WebPage) Repositories.subjects().get(association.getSubjectId().toString());
        return webPage != null;
    }

    private WebPage doCreateWebPage(final Association association) {
        final WebPage webPage = new WebPage(association);
        webPage.setScraper(new UriScraper());
        DomainEventBus.INSTANCE.spread(new WebPageCreatedEvent(webPage));
        return webPage;
    }
}
