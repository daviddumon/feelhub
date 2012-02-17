package com.steambeat.domain.subject;

import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.scrapers.Scraper;
import org.joda.time.DateTime;

public abstract class Subject extends BaseEntity {

    // Mongolink constructor : do not delete
    protected Subject() {
    }

    protected Subject(final String id) {
        this.id = id;
        this.creationDate = new DateTime();
    }

    public void update(final Scraper scraper) {
        description = scraper.getDescription();
        shortDescription = scraper.getShortDescription();
        illustration = scraper.getIllustration();
        scrapedDataExpirationDate = new DateTime().plusDays(1);
    }

    @Override
    public String getId() {
        return id;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getIllustration() {
        return illustration;
    }

    public DateTime getScrapedDataExpirationDate() {
        return scrapedDataExpirationDate;
    }

    public boolean isExpired() {
        return !scrapedDataExpirationDate.isAfter(new DateTime());
    }

    private String id;
    private DateTime creationDate;
    protected String description;
    protected String shortDescription;
    protected String illustration;
    protected DateTime scrapedDataExpirationDate;
}
