package com.steambeat.domain.subject.webpage;

import com.google.common.collect.Lists;
import com.steambeat.tools.*;
import org.restlet.*;
import org.restlet.data.*;

import java.util.List;

public class CanonicalUriFinder {

    public Uri find(final Uri uri) {
        return followRedirection(uri);
    }

    private Uri followRedirection(final Uri uri) {
        final Client client = Clients.create();
        try {
            return doFollow(uri, client);
        } catch (Exception e) {
            throw new WebPageException(e);
        } finally {
            Clients.stop(client);
        }
    }

    private Uri doFollow(final Uri uri, final Client client) {
        String currentUri = uri.toString();
        Response response = null;
        do {
            final Request request = Requests.create(Method.HEAD, currentUri);
            response = client.handle(request);
            if (response.getStatus().isRedirection()) {
                currentUri = response.getLocationRef().toString();
            }
            if (notExistingResource(response)) {
                throw new WebPageException(uri, response.getStatus());
            }
        } while (response.getStatus().isRedirection());
        return new Uri(currentUri);
    }

    private boolean notExistingResource(final Response response) {
        return ERROR_STATUS.contains(response.getStatus());
    }

    private static final List<Status> ERROR_STATUS = Lists.newArrayList(Status.CONNECTOR_ERROR_COMMUNICATION,
            Status.CONNECTOR_ERROR_CONNECTION,
            Status.CLIENT_ERROR_NOT_FOUND);

}
