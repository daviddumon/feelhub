package com.steambeat.web;

import org.restlet.Context;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

public class FeedExceptionResolver implements DomainErrorResolver {

    @Override
    public Status getStatus(final Throwable throwable) {
        return new Status(Status.CLIENT_ERROR_BAD_REQUEST, throwable);
    }

    @Override
    public Representation getRepresentation(final Context context) {
        return SteambeatTemplateRepresentation.createNew("error.ftl", context);
    }
}
