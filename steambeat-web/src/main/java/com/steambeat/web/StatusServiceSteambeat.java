package com.steambeat.web;

import com.google.common.collect.Maps;
import com.steambeat.domain.subject.feed.*;
import org.restlet.*;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.service.StatusService;

import java.util.Map;

public class StatusServiceSteambeat extends StatusService {

    public StatusServiceSteambeat() {
        resolvers.put(FeedException.class, new FeedExceptionResolver());
        resolvers.put(FeedAlreadyExistsException.class, new FeedExceptionResolver());
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

    private DomainErrorResolver resolverFor(final Throwable throwable) {
        return resolvers.get(throwable.getClass());
    }

    @Override
    public Representation getRepresentation(final Status status, final Request request, final Response response) {
        if (canResolve(status.getThrowable())) {
            return resolverFor(status.getThrowable()).getRepresentation(getContext());
        }
        return super.getRepresentation(status, request, response);
    }

    private final Map<Class<?>, DomainErrorResolver> resolvers = Maps.newHashMap();
}
