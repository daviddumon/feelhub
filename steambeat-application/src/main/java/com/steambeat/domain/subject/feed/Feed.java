package com.steambeat.domain.subject.feed;

import com.steambeat.domain.subject.Subject;

public class Feed extends Subject {

    protected Feed() {
    }

    public Feed(final Association association) {
        super(association.getCanonicalUri());
    }

    public String getUri() {
        return getId();
    }

    public Uri getRealUri() {
        return new Uri(getId());
    }
}
