package com.steambeat.domain.subject.webpage;

public class WebPageAlreadyExistsException extends WebPageException {

    public WebPageAlreadyExistsException(final String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    private final String uri;
}
