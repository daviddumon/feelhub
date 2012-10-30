package com.feelhub.web.status;

import freemarker.template.Configuration;
import org.restlet.*;
import org.restlet.data.*;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;

public class GenericExceptionResolver implements ErrorResolver {

    @Override
    public Status getStatus(final Throwable throwable) {
        return new Status(Status.CLIENT_ERROR_NOT_FOUND, throwable);
    }

    @Override
    public Representation getRepresentation(final Context context, final Request request, final String message) {
        return new TemplateRepresentation("error.ftl", (Configuration) context.getAttributes().get("org.freemarker.Configuration"), MediaType.TEXT_HTML);
    }
}
