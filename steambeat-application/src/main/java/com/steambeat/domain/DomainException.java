package com.steambeat.domain;

public class DomainException extends RuntimeException {

    public DomainException() {
        super("");
    }

    public DomainException(final String message) {
        super(message);
    }

    public DomainException(final Exception e) {
        super(e);
    }
}
