package com.steambeat.domain.user;

import com.steambeat.domain.DomainException;

public class UserException extends DomainException {

    public UserException() {
        super("");
    }

    public UserException(final String message) {
        super(message);
    }
}
