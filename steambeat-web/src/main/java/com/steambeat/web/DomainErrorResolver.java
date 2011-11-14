package com.steambeat.web;

import org.restlet.Context;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

public interface DomainErrorResolver {
    Status getStatus(Throwable throwable);

    Representation getRepresentation(Context context);
}
