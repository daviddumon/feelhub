package com.steambeat.application;

import com.google.inject.Inject;
import com.steambeat.domain.scrapers.UriScraper;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.repositories.Repositories;

public class WebPageService {

    @Inject
    public WebPageService(final AssociationService associationService, final WebPageFactory webPageFactory) {
        this.associationService = associationService;
        this.webPageFactory = webPageFactory;
    }

    public WebPage lookUpWebPage(final Uri uri) {
        WebPage webPage;
        final Association association = associationService.lookUp(uri);
        webPage = Repositories.webPages().get(association.getCanonicalUri());
        if (webPage == null) {
            throw new WebPageNotYetCreatedException();
        } else {
            checkScrapedData(webPage);
        }
        return webPage;
    }

    private void checkScrapedData(final WebPage webPage) {
        if (webPage.isExpired()) {
            final UriScraper uriScraper = new UriScraper(webPage.getRealUri());
            uriScraper.scrap();
            webPage.update(uriScraper);
        }
    }

    public WebPage addWebPage(final Uri uri) {
        final Association association = associationService.lookUp(uri);
        final WebPage webPage = webPageFactory.newWebPage(association);
        Repositories.webPages().add(webPage);
        return webPage;
    }

    private final AssociationService associationService;
    private final WebPageFactory webPageFactory;
}
