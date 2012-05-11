package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.association.Association;
import com.steambeat.domain.association.uri.Uri;
import com.steambeat.domain.scrapers.Scraper;
import com.steambeat.domain.subject.Subject;
import org.joda.time.DateTime;

public class WebPage extends Subject {

    // Mongolink constructor : do not delete
    public WebPage() {
    }

    public WebPage(final Association association) {
        super(association.getSubjectId());
        uri = association.getIdentifier();
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

    @Override
    public String getUriToken() {
        return "/webpages/";
    }

    public Uri getRealUri() {
        return new Uri(uri);
    }

    public String getUri() {
        return uri;
    }

    private String uri;
}
