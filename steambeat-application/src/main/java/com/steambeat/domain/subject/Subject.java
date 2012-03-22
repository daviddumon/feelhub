package com.steambeat.domain.subject;

import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.scrapers.Scraper;
import org.joda.time.DateTime;

import java.util.UUID;

public abstract class Subject extends BaseEntity {

    // Mongolink constructor : do not delete
    protected Subject() {
    }

    protected Subject(final UUID id) {
        this.id = id;
        this.creationDate = new DateTime();
    }

    public abstract void setScraper(final Scraper scraper);

    protected abstract void update(final Scraper scraper);

    @Override
    public UUID getId() {
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

    private UUID id;
    private DateTime creationDate;
    protected String description;
    protected String shortDescription;
    protected String illustration;
    protected DateTime scrapedDataExpirationDate;
}
