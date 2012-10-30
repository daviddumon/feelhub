package com.feelhub.domain.user;

import com.feelhub.domain.DomainException;

public class UserException extends DomainException {

    public UserException() {
        super("");
    }

    public UserException(final String message) {
        super(message);
    }
}
