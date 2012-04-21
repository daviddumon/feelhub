package com.steambeat.domain.subject.webpage;

import com.google.inject.Inject;
import com.steambeat.domain.DomainEventBus;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.alchemy.AlchemyEntityAnalyzer;
import com.steambeat.domain.scrapers.UriScraper;
import com.steambeat.repositories.Repositories;

public class WebPageFactory {

    @Inject
    public WebPageFactory(final UriScraper uriScraper, final AlchemyEntityAnalyzer alchemyEntityAnalyzer) {
        this.uriScraper = uriScraper;
        this.alchemyEntityAnalyzer = alchemyEntityAnalyzer;
    }

    public WebPage newWebPage(final Association association) {
        if (checkIfExists(association)) {
            throw new WebPageAlreadyExistsException(association.getId());
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
        DomainEventBus.INSTANCE.spread(new WebPageCreatedEvent(webPage));
        alchemyEntityAnalyzer.analyze(webPage);
        return webPage;
    }

    private UriScraper uriScraper;
    private AlchemyEntityAnalyzer alchemyEntityAnalyzer;
}
