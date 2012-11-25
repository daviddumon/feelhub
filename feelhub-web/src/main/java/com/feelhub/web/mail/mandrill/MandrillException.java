package com.feelhub.web.mail.mandrill;

import org.restlet.data.Status;

public class MandrillException extends RuntimeException {
    public MandrillException(final Status status, final String entityAsText) {
        super(String.format("Can't send mail to mandrill %s : %s", status, entityAsText));
    }
}
