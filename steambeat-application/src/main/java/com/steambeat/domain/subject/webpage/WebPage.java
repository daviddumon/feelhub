package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.subject.Subject;
import org.joda.time.DateTime;

public class WebPage extends Subject {

    protected WebPage() {
    }

    public WebPage(final Association association) {
        super(association.getCanonicalUri());
    }

    public Uri getRealUri() {
        return new Uri(getId());
    }

    public boolean isExpired() {
        return !scrapedDataExpirationDate.isAfter(new DateTime());
    }
}
