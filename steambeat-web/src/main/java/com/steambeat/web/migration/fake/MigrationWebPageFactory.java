package com.steambeat.web.migration.fake;

import com.steambeat.domain.DomainEventBus;
import com.steambeat.domain.association.Association;
import com.steambeat.domain.subject.webpage.*;

public class MigrationWebPageFactory extends WebPageFactory {

    public MigrationWebPageFactory() {
        super(new MigrationUriScraper(), new MigrationAlchemyEntityAnalyzer());
    }

    @Override
    public WebPage newWebPage(final Association association) {
        if (checkIfExists(association)) {
            throw new WebPageAlreadyExistsException(association.getIdentifier());
        }
        final WebPage webPage = new WebPage(association);
        webPage.setScraper(new MigrationUriScraper());
        DomainEventBus.INSTANCE.spread(new WebPageCreatedEvent(webPage));
        return webPage;
    }
}
