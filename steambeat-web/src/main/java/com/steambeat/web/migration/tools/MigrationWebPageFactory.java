package com.steambeat.web.migration.tools;

import com.steambeat.domain.DomainEventBus;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.subject.webpage.*;

public class MigrationWebPageFactory extends WebPageFactory {

    public MigrationWebPageFactory() {
        super();
    }

    @Override
    public WebPage newWebPage(final Association association) {
        if (checkIfExists(association)) {
            throw new WebPageAlreadyExistsException(association.getId());
        }
        final WebPage webPage = new WebPage(association);
        webPage.setScraper(new MigrationUriScraper());
        DomainEventBus.INSTANCE.spread(new WebPageCreatedEvent(webPage));
        return webPage;
    }
}
