package com.feelhub.web;

import org.restlet.*;
import org.restlet.data.*;
import org.restlet.engine.util.CookieSeries;
import org.restlet.representation.Representation;
import org.restlet.resource.UniformResource;
import org.restlet.service.ConverterService;

public class ClientResource extends UniformResource {

    public ClientResource(final String uri, final Restlet application) {
        this.reference = new Reference(uri);
        this.application = application;
    }

    public ClientResource(final String uri, final Restlet application, final ChallengeResponse challengeResponse) {
        this(uri, application);
        this.challengeResponse = challengeResponse;
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

    public Representation post(final Object entity, final CookieSeries cookies) {
        initHandling(Method.POST);
        getRequest().setEntity(getRepresentation(entity));
        getRequest().setCookies(cookies);
        return handle();
    }

    public Representation delete(final CookieSeries cookies) {
        initHandling(Method.DELETE);
        if (cookies != null) {
            getRequest().setCookies(cookies);
        }
        return handle();
    }

    private void initHandling(final Method Method) {
        setRequest(new Request(Method, reference));
        setResponse(new Response(getRequest()));
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
        final Request request = getRequest();
        if (withSecurity()) {
            request.setChallengeResponse(challengeResponse);
        }
        application.handle(request, getResponse());
        return getResponse().getEntity();
    }

    private boolean withSecurity() {
        return challengeResponse != null;
    }

    private final Reference reference;
    private final Restlet application;
    private ChallengeResponse challengeResponse;
}
