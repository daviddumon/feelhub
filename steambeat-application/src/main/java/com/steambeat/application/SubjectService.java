package com.steambeat.application;

import com.google.inject.Inject;
import com.steambeat.domain.association.Association;
import com.steambeat.domain.scrapers.UriScraper;
import com.steambeat.domain.subject.*;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class SubjectService {

    @Inject
    public SubjectService(final SubjectFactory subjectFactory) {
        this.subjectFactory = subjectFactory;
    }

    public Subject lookUpSubject(final UUID subjectId) {
        final Subject subject = subjectFactory.lookUpSubject(subjectId);
        if (subject == null) {
            throw new SubjectNotYetCreatedException();
        }
        return subject;
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
