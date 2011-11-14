package com.steambeat.domain.subject.feed;

import com.steambeat.domain.DomainException;
import org.restlet.data.Status;

public class FeedException extends DomainException {

    public FeedException() {
    }

    public FeedException(final Exception e) {
        super(e);
    }

    public FeedException(final Uri uri, final Status status) {
        super(uri.toString() + ": " + status.getCode() + " " + status.getDescription());
    }
}
