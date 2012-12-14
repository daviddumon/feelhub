package com.feelhub.domain.topic.http.uri;

import com.feelhub.domain.DomainException;
import org.restlet.data.Status;

public class UriException extends DomainException {

    public UriException(final Exception e) {
        super(e);
    }

    public UriException(final String uri, final Status status) {
        super(uri + ": " + status.getCode() + " " + status.getDescription());
        System.out.println("URI RESOLVER ERROR : " + uri + " - " + status.getName());
    }
}
