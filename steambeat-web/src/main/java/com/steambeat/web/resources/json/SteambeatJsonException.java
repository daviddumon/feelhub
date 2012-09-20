package com.steambeat.web.resources.json;

public class SteambeatJsonException extends RuntimeException {

    public SteambeatJsonException() {
        message = "";
    }

    public SteambeatJsonException(final String message) {
        this.message = message;
    }

    private String message;
}
