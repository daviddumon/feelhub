package com.steambeat.web.status;

import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.restlet.*;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

public class GenericExceptionResolver implements ErrorResolver {

    @Override
    public Status getStatus(final Throwable throwable) {
        return new Status(Status.CLIENT_ERROR_NOT_FOUND, throwable);
    }

    @Override
    public Representation getRepresentation(final Context context, final Request request) {
        return SteambeatTemplateRepresentation.createNew("error.ftl", context, request);
    }
}
