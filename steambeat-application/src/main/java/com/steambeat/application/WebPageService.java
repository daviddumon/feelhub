package com.steambeat.application;

import com.google.inject.Inject;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.repositories.Repositories;

public class WebPageService {

    @Inject
    public WebPageService(final AssociationService associationService, final WebPageFactory webPageFactory) {
        this.associationService = associationService;
        this.webPageFactory = webPageFactory;
    }

    public WebPage lookUpWebPage(final Uri uri) {
        final Association association = associationService.lookUp(uri.toString());
        final WebPage webPage = Repositories.webPages().get(association.getCanonicalUri());
        if (webPage == null) {
            throw new WebPageNotYetCreatedException();
        }
        return webPage;
    }

    public WebPage addWebPage(final Uri uri) {
        final Association association = associationService.lookUp(uri.toString());
        final WebPage result = webPageFactory.buildWebPage(association);
        Repositories.webPages().add(result);
        return result;
    }

    private final AssociationService associationService;
    private final WebPageFactory webPageFactory;
}
