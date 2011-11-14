package com.steambeat.domain.subject.feed;

public class FeedAlreadyExistsException extends FeedException {

    public FeedAlreadyExistsException(final String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    private String uri;
}
