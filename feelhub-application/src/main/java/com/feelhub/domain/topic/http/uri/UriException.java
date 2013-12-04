package com.feelhub.domain.topic.http.uri;

import com.feelhub.domain.DomainException;
import org.restlet.data.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UriException extends DomainException {

    public UriException(final Exception e) {
        super(e);
    }

    public UriException(final String uri, final Status status) {
        super(uri + ": " + status.getCode() + " " + status.getDescription());
	LOGGER.error("URI RESOLVER ERROR : " + uri + " - " + status.getName());
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(UriException.class);
}
