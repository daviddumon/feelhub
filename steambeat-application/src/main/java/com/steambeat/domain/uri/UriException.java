package com.steambeat.domain.uri;

import com.steambeat.domain.DomainException;
import org.restlet.data.Status;

public class UriException extends DomainException {

    public UriException(final Exception e) {
        super(e);
    }

    public UriException(final String uri, final Status status) {
        super(uri + ": " + status.getCode() + " " + status.getDescription());
    }
}
