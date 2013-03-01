package com.feelhub.application.mail;

import com.feelhub.domain.DomainException;

public class EmailException extends DomainException {

    public EmailException() {
        super();
    }

    public EmailException(final Exception e) {
        super(e);
    }

}
