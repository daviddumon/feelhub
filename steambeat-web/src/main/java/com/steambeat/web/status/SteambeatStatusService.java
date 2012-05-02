package com.steambeat.web.status;

import com.google.common.collect.Maps;
import com.steambeat.application.*;
import com.steambeat.domain.association.uri.UriPathResolverException;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.web.resources.*;
import org.restlet.*;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.service.StatusService;

import java.util.Map;

public class SteambeatStatusService extends StatusService {

    public SteambeatStatusService() {
        resolvers.put(WebPageException.class, new ExceptionResolver400());
        resolvers.put(UriPathResolverException.class, new ExceptionResolver400());
        resolvers.put(WebPageAlreadyExistsException.class, new ExceptionResolver400());
        resolvers.put(SteambeatJsonException.class, new JsonExceptionResolver());
        resolvers.put(WebPageNotYetCreatedException.class, new ExceptionResolver404());
        resolvers.put(OpinionCreationException.class, new ExceptionResolver400());
        resolvers.put(AssociationNotFound.class, new ExceptionResolver404());
        resolvers.put(BookmarkletBadVersion.class, new BookmarkletBadVersionResolver());
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
        if (canResolve(status.getThrowable())) {
            return resolverFor(status.getThrowable()).getRepresentation(getContext());
        }
        return new GenericExceptionResolver().getRepresentation(getContext());
    }

    private final Map<Class<?>, ErrorResolver> resolvers = Maps.newHashMap();
}
