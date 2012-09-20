package com.steambeat.web.status;

import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.restlet.*;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

public class ExceptionResolver404 implements ErrorResolver {

    @Override
    public Status getStatus(final Throwable throwable) {
        return Status.CLIENT_ERROR_NOT_FOUND;
    }

    @Override
    public Representation getRepresentation(final Context context, final Request request, final String message) {
        return SteambeatTemplateRepresentation.createNew("error.ftl", context, request);
    }
}
