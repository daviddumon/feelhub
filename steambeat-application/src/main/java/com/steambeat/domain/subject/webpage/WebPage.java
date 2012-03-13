package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.scrapers.Scraper;
import com.steambeat.domain.subject.Subject;
import org.joda.time.DateTime;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WebPage extends Subject {

    // Mongolink constructor : do not delete
    public WebPage() {
    }

    public WebPage(final Association association) {
        super(association.getSubjectId().toString());
        uri = association.getId();
    }

    public void setScraper(final Scraper scraper) {
        this.scraper = scraper;
        scraper.scrap(getRealUri());
        update();
    }

    @Override
    public void update() {
        description = scraper.getDescription();
        shortDescription = scraper.getShortDescription();
        illustration = scraper.getIllustration();
        scrapedDataExpirationDate = new DateTime().plusDays(1);
        buildSemanticDescription();
    }

    private void buildSemanticDescription() {
        final String decodedSemanticDescription = getRealUri().getDomain().replaceAll("\\.", "-") + "-" + description.replaceAll("\\ ", "-");
        try {
            semanticDescription = URLEncoder.encode(decodedSemanticDescription, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new WebPageException();
        }
    }

    public Uri getRealUri() {
        return new Uri(uri);
    }

    public String getUri() {
        return uri;
    }

    private String uri;
    private Scraper scraper;
}
