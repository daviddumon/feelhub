package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.DomainEventBus;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.scrapers.UriScraper;
import com.steambeat.repositories.Repositories;

public class WebPageFactory {

    public WebPage newWebPage(final Association association) {
        if (checkIfExists(association)) {
            throw new WebPageAlreadyExistsException(association.getId());
        }
        return doCreateWebPage(association);
    }

    private boolean checkIfExists(final Association association) {
        final WebPage webPage = Repositories.webPages().get(association.getSubjectId().toString());
        if (webPage != null) {
            return true;
        }
        return false;
    }

    private WebPage doCreateWebPage(final Association association) {
        final WebPage webPage = new WebPage(association);
        uriScraper.scrap(new Uri(association.getId()));
        webPage.update(uriScraper);
        DomainEventBus.INSTANCE.spread(new WebPageCreatedEvent(webPage));
        return webPage;
    }

    private UriScraper uriScraper = new UriScraper();
}
