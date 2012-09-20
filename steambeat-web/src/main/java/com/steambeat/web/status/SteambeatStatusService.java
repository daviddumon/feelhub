package com.steambeat.web.status;

import com.google.common.collect.Maps;
import com.steambeat.domain.opinion.OpinionCreationException;
import com.steambeat.domain.uri.UriException;
import com.steambeat.web.resources.json.SteambeatJsonException;
import org.restlet.*;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.service.StatusService;

import java.util.Map;

public class SteambeatStatusService extends StatusService {

    public SteambeatStatusService() {
        resolvers.put(UriException.class, new ExceptionResolver400());
        resolvers.put(SteambeatJsonException.class, new JsonExceptionResolver());
        resolvers.put(OpinionCreationException.class, new ExceptionResolver400());
        //resolvers.put(BadUserException.class, new StringExceptionResolver());
    }

    @Override
    public Status getStatus(final Throwable throwable, final Request request, final Response response) {
        if (canResolve(throwable)) {
            return resolverFor(throwable).getStatus(throwable);
        }
        return super.getStatus(throwable, request, response);
    }

    private boolean canResolve(final Throwable throwable) {
        if (throwable != null) {
            return resolvers.containsKey(throwable.getClass());
        }
        return false;
    }

    private ErrorResolver resolverFor(final Throwable throwable) {
        return resolvers.get(throwable.getClass());
    }

    @Override
    public Representation getRepresentation(final Status status, final Request request, final Response response) {
        String message = getMessage(status);
        if (canResolve(status.getThrowable())) {
            return resolverFor(status.getThrowable()).getRepresentation(getContext(), request, message);
        }
        return new GenericExceptionResolver().getRepresentation(getContext(), request, message);
    }

    private String getMessage(final Status status) {
        String message = "";
        if (status.getThrowable() != null) {
            message = status.getThrowable().getMessage();
        }
        return message;
    }

    private final Map<Class<?>, ErrorResolver> resolvers = Maps.newHashMap();
}
