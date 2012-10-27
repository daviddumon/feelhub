package com.steambeat.web.status;

import freemarker.template.Configuration;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;

public class ExceptionResolver400 implements ErrorResolver {

    @Override
    public Status getStatus(final Throwable throwable) {
        return new Status(Status.CLIENT_ERROR_BAD_REQUEST, throwable);
    }

    @Override
    public Representation getRepresentation(final Context context, final Request request, final String message) {
		return new TemplateRepresentation("error.ftl", (Configuration) context.getAttributes().get("org.freemarker.Configuration"), MediaType.TEXT_HTML);
    }
}
