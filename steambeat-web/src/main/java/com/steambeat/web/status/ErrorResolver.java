package com.steambeat.web.status;

import org.restlet.Context;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

public interface ErrorResolver {
    Status getStatus(Throwable throwable);

    Representation getRepresentation(Context context);
}
