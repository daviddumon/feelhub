package com.feelhub.web.status;

import org.restlet.*;
import org.restlet.data.Status;
import org.restlet.representation.*;

public class UserExceptionResolver implements ErrorResolver {

    @Override
    public Status getStatus(final Throwable throwable) {
        return new Status(Status.CLIENT_ERROR_FORBIDDEN, throwable);
    }

    @Override
    public Representation getRepresentation(final Context context, final Request request, final String message) {
        return new StringRepresentation(message);
    }
}
