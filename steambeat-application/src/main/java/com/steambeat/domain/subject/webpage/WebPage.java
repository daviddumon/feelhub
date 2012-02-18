package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.scrapers.*;
import com.steambeat.domain.subject.Subject;
import org.joda.time.DateTime;

public class WebPage extends Subject {

    // Mongolink constructor : do not delete
    public WebPage() {
    }

    public WebPage(final Association association) {
        super(association.getSubjectId().toString());
        this.uri = association.getId();
        scrapMe(association);
    }

    private void scrapMe(final Association association) {
        this.scraper = new UriScraper();
        scraper.scrap(new Uri(association.getId()));
        update();
    }

    @Override
    public void update() {
        description = scraper.getDescription();
        shortDescription = scraper.getShortDescription();
        illustration = scraper.getIllustration();
        scrapedDataExpirationDate = new DateTime().plusDays(1);
    }

    public Uri getRealUri() {
        return new Uri(uri);
    }

    private String uri;
    private Scraper scraper;
}
