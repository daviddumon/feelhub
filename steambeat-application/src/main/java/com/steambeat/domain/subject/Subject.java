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
        lastModificationDate = creationDate;
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

    public DateTime getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(final DateTime lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setShortDescription(final String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public abstract String getUriToken();

    private UUID id;
    protected DateTime lastModificationDate;
    protected DateTime creationDate;
    protected String description;
    protected String shortDescription;
    protected String illustration;
    protected DateTime scrapedDataExpirationDate;
}
