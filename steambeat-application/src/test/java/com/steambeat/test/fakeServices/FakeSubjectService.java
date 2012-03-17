package com.steambeat.test.fakeServices;

import com.google.inject.Inject;
import com.steambeat.application.SubjectService;
import com.steambeat.domain.subject.SubjectFactory;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.FakeUriScraper;

public class FakeSubjectService extends SubjectService {

    @Inject
    public FakeSubjectService(final SubjectFactory subjectFactory) {
        super(subjectFactory);
    }

    @Override
    protected void checkScrapedData(final WebPage webPage) {
        if (webPage.isExpired()) {
            webPage.setScraper(new FakeUriScraper());
            webPage.update();
        }
    }
}
