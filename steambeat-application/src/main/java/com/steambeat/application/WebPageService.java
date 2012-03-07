package com.steambeat.application;

import com.google.inject.Inject;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class WebPageService {

    @Inject
    public WebPageService(final WebPageFactory webPageFactory) {
        this.webPageFactory = webPageFactory;
    }

    public WebPage lookUpWebPage(final UUID subjectId) {
        final WebPage webPage = Repositories.webPages().get(subjectId.toString());
        if (webPage == null) {
            throw new WebPageNotYetCreatedException();
        } else {
            checkScrapedData(webPage);
        }
        return webPage;
    }

    private void checkScrapedData(final WebPage webPage) {
        if (webPage.isExpired()) {
            webPage.update();
        }
    }

    public WebPage addWebPage(final Association association) {
        WebPage webPage;
        try {
            webPage = webPageFactory.newWebPage(association);
            Repositories.webPages().add(webPage);
        } catch (WebPageAlreadyExistsException e) {
            webPage = lookUpWebPage(association.getSubjectId());
        }
        return webPage;
    }

    private final WebPageFactory webPageFactory;
}
