package com.feelhub.domain;

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

    public DomainException(final String message, final Exception e) {
        super(message, e);
    }

}
