package com.feelhub.domain.user;

public class BadUserException extends UserException {

    public BadUserException() {
        super("");
    }

    public BadUserException(final String message) {
        super(message);
    }
}
