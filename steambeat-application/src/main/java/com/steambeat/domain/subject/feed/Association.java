package com.steambeat.domain.subject.feed;

import com.steambeat.domain.BaseEntity;
import org.joda.time.DateTime;

public class Association extends BaseEntity {

    protected Association() {
    }

    public Association(final Uri uri, final CanonicalUriFinder finder) {
        this.uri = uri.toString();
        expirationDate = new DateTime();
        if (finder != null) {
            update(finder);
        } else {
            canonicalUri = uri.toString();
        }
    }

    public String getUri() {
        return uri;
    }

    public String getCanonicalUri() {
        return canonicalUri;
    }

    public DateTime getExpirationDate() {
        return expirationDate;
    }

    public boolean isAlive() {
        return expirationDate.isAfter(new DateTime());
    }

    public void update(CanonicalUriFinder finder) {
        if (!isAlive()) {
            canonicalUri = finder.find(new Uri(uri)).toString();
            expirationDate = expirationDate.plusDays(7);
        }
    }

    @Override
    public String getId() {
        return uri;
    }

    private String uri;
    private String canonicalUri;
    private DateTime expirationDate;
}
