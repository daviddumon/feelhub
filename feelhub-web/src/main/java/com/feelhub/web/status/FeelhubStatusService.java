package com.feelhub.web.status;

import com.feelhub.domain.feeling.FeelingCreationException;
import com.feelhub.domain.uri.UriException;
import com.feelhub.domain.user.BadUserException;
import com.feelhub.web.resources.json.FeelhubJsonException;
import com.google.common.collect.Maps;
import org.restlet.*;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.service.StatusService;

import java.util.Map;

public class FeelhubStatusService extends StatusService {

    public FeelhubStatusService() {
        resolvers.put(UriException.class, new ExceptionResolver400());
        resolvers.put(FeelhubJsonException.class, new JsonExceptionResolver());
        resolvers.put(FeelingCreationException.class, new ExceptionResolver400());
        resolvers.put(BadUserException.class, new UserExceptionResolver());
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
        final String message = getMessage(status);
        if (canResolve(status.getThrowable())) {
            return resolverFor(status.getThrowable()).getRepresentation(getContext(), request, message);
        }
        return new GenericExceptionResolver().getRepresentation(getContext(), request, message);
    }

    private String getMessage(final Status status) {
        String message = "error";
        if (status.getThrowable() != null) {
            if (status.getThrowable().getMessage() != null && !status.getThrowable().getMessage().isEmpty()) {
                message = status.getThrowable().getMessage();
            }
        }
        return message;
    }

    private final Map<Class<?>, ErrorResolver> resolvers = Maps.newHashMap();
}
