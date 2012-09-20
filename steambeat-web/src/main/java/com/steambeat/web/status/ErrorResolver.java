package com.steambeat.web.status;

import org.restlet.*;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

public interface ErrorResolver {

    Status getStatus(Throwable throwable);

    Representation getRepresentation(Context context, Request request, final String message);
}
