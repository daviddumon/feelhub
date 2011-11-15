package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.subject.Subject;

public class WebPage extends Subject {

    protected WebPage() {
    }

    public WebPage(final Association association) {
        super(association.getCanonicalUri());
    }

    public String getUri() {
        return getId();
    }

    public Uri getRealUri() {
        return new Uri(getId());
    }
}
