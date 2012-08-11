package com.steambeat.domain.uri;

import com.steambeat.domain.DomainException;
import org.restlet.data.Status;

public class UriPathResolverException extends DomainException {

    public UriPathResolverException(final Exception e) {
        super(e);
    }

    public UriPathResolverException(final Uri uri, final Status status) {
        super(uri.toString() + ": " + status.getCode() + " " + status.getDescription());
    }
}
