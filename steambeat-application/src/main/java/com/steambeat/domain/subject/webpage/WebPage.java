package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.scrapers.UriScraper;
import com.steambeat.domain.subject.Subject;

public class WebPage extends Subject {

    protected WebPage() {
    }

    public WebPage(final Association association, final UriScraper uriScraper) {
        super(association.getCanonicalUri());
        this.description = uriScraper.getDescription();
        this.shortDescription = uriScraper.getShortDescription();
        this.illustration = uriScraper.getIllustration();
    }

    public Uri getRealUri() {
        return new Uri(getId());
    }
}
