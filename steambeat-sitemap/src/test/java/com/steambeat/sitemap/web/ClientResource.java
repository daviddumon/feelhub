package com.steambeat.sitemap.web;

import org.restlet.*;
import org.restlet.data.*;
import org.restlet.representation.Representation;
import org.restlet.resource.UniformResource;

public class ClientResource extends UniformResource {

    public ClientResource(final String uri, final Restlet application) {
        this.reference = new Reference(uri);
        this.application = application;
    }

    public Representation get() {
        initHandling(Method.GET);
        return handle();
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
        final org.restlet.service.ConverterService cs = getConverterService();
        return cs.toRepresentation(source);
    }

    private void initHandling(final Method Method) {
        setRequest(new Request(Method, reference));
        setResponse(new Response(getRequest()));
    }

    @Override
    public Representation handle() {
        application.handle(getRequest(), getResponse());
        return getResponse().getEntity();
    }

    private final Restlet application;
    private Reference reference;
}
