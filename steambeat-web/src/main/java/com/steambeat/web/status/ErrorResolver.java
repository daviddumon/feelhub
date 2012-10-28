package com.steambeat.web.status;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;

public interface ErrorResolver {

    Status getStatus(Throwable throwable);

    Representation getRepresentation(Context context, Request request, final String message);
}
