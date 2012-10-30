package com.feelhub.web.resources.json;

public class FeelhubJsonException extends RuntimeException {

    public FeelhubJsonException() {
        message = "";
    }

    public FeelhubJsonException(final String message) {
        this.message = message;
    }

    private final String message;
}
