package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.scrapers.Scraper;
import com.steambeat.domain.subject.Subject;
import org.joda.time.DateTime;

public class WebPage extends Subject {

    // Mongolink constructor : do not delete
    public WebPage() {
    }

    public WebPage(final Association association) {
        super(association.getSubjectId());
        uri = association.getId();
    }

    public void setScraper(final Scraper scraper) {
        scraper.scrap(getRealUri());
        update(scraper);
    }

    @Override
    protected void update(final Scraper scraper) {
        description = scraper.getDescription();
        shortDescription = scraper.getShortDescription();
        illustration = scraper.getIllustration();
        scrapedDataExpirationDate = new DateTime().plusDays(1);
    }

    public Uri getRealUri() {
        return new Uri(uri);
    }

    public String getUri() {
        return uri;
    }

    private String uri;
}
