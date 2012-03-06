package com.steambeat.domain.subject;

import com.steambeat.domain.BaseEntity;
import org.joda.time.DateTime;

public abstract class Subject extends BaseEntity {

    // Mongolink constructor : do not delete
    protected Subject() {
    }

    protected Subject(final String id) {
        this.id = id;
        this.creationDate = new DateTime();
    }

    public abstract void update();

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

    public String getSemanticDescription() {
        return semanticDescription;
    }

    private String id;
    private DateTime creationDate;
    protected String description;
    protected String shortDescription;
    protected String illustration;
    protected DateTime scrapedDataExpirationDate;
    protected String semanticDescription;
}
