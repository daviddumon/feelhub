package com.steambeat.application;

import com.google.inject.Inject;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.scrapers.UriScraper;
import com.steambeat.domain.subject.SubjectFactory;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class SubjectService {

    @Inject
    public SubjectService(final SubjectFactory subjectFactory) {
        this.subjectFactory = subjectFactory;
    }

    public WebPage lookUpWebPage(final UUID subjectId) {
        final WebPage webPage = subjectFactory.lookUpWebpage(subjectId);
        if (webPage == null) {
            throw new WebPageNotYetCreatedException();
        } else {
            checkScrapedData(webPage);
        }
        return webPage;
    }

    protected void checkScrapedData(final WebPage webPage) {
        if (webPage.isExpired()) {
            webPage.setScraper(new UriScraper());
        }
    }

    public WebPage addWebPage(final Association association) {
        WebPage webPage;
        try {
            webPage = subjectFactory.newWebPage(association);
            Repositories.subjects().add(webPage);
        } catch (WebPageAlreadyExistsException e) {
            webPage = lookUpWebPage(association.getSubjectId());
        }
        return webPage;
    }

    protected final SubjectFactory subjectFactory;
}
