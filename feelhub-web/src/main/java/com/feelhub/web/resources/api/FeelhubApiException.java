package com.feelhub.web.resources.api;

public class FeelhubApiException extends RuntimeException {

    public FeelhubApiException() {
    }

    public FeelhubApiException(final String message) {
        super(message);
    }
}
