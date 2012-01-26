package com.steambeat.web.status;

import org.restlet.*;
import org.restlet.data.*;
import org.restlet.ext.json.*;
import org.restlet.representation.*;

public class JsonExceptionResolver implements ErrorResolver {

    @Override
    public Status getStatus(Throwable throwable) {
        return new Status(Status.CLIENT_ERROR_BAD_REQUEST, throwable);
    }

    @Override
    public Representation getRepresentation(Context context) {
        return new JsonRepresentation("");
    }
}
