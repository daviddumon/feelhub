package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.scrapers.WebPageScraper;
import com.steambeat.domain.subject.Subject;

public class WebPage extends Subject {

    protected WebPage() {
    }

    public WebPage(final Association association, final WebPageScraper webPageScraper) {
        super(association.getCanonicalUri());
    }

    public Uri getRealUri() {
        return new Uri(getId());
    }

    @Override
    protected String getTitle() {
        return "";
    }

    @Override
    protected String getShortTitle() {
        return "";
    }

    @Override
    protected String getThumbnailUrl() {
        return "";
    }

}
