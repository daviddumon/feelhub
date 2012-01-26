package com.steambeat.web;

import org.restlet.*;
import org.restlet.data.*;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.restlet.service.ConverterService;

public class ClientResource extends UniformResource {

    public ClientResource(final String uri, final Restlet application) {
        this.reference = new Reference(uri);
        this.application = application;
    }

    public Representation get() {
        initHandling(Method.GET);
        return handle();
    }

    private void initHandling(final Method Method) {
        setRequest(new Request(Method, reference));
        setResponse(new Response(getRequest()));
    }

    public Representation post(final Object entity) {
        initHandling(Method.POST);
        getRequest().setEntity(getRepresentation(entity));
        return handle();
    }

    private Representation getRepresentation(final Object source) {
        if (source == null) {
            return null;
        }
        final ConverterService cs = getConverterService();
        return cs.toRepresentation(source);
    }

    @Override
    public Representation handle() {
        application.handle(getRequest(), getResponse());
        return getResponse().getEntity();
    }

    private final Reference reference;
    private final Restlet application;
}
