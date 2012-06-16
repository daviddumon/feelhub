package com.steambeat.web.status;

import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.restlet.Context;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

public class BookmarkletBadVersionResolver implements ErrorResolver {

    @Override
    public Status getStatus(final Throwable throwable) {
        return new Status(Status.CLIENT_ERROR_BAD_REQUEST, throwable);
    }

    @Override
    public Representation getRepresentation(final Context context) {
        return SteambeatTemplateRepresentation.createNew("/old/version.ftl", context);
    }
}
