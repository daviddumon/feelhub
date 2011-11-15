package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.DomainException;
import org.restlet.data.Status;

public class WebPageException extends DomainException {

    public WebPageException() {
    }

    public WebPageException(final Exception e) {
        super(e);
    }

    public WebPageException(final Uri uri, final Status status) {
        super(uri.toString() + ": " + status.getCode() + " " + status.getDescription());
    }
}
