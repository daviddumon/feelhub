package com.steambeat.domain.uri;

import com.google.common.collect.Lists;
import com.steambeat.tools.*;
import org.restlet.*;
import org.restlet.data.*;

import java.util.List;

public class UriResolver {

    public List<String> resolve(final String uri) {
        path.add(uri);
        followRedirection(uri);
        return path;
    }

    private String followRedirection(final String uri) {
        final Client client = Clients.create();
        try {
            return doFollow(uri, client);
        } catch (Exception e) {
            throw new UriException(e);
        } finally {
            Clients.stop(client);
        }
    }

    private String doFollow(final String uri, final Client client) {
        String currentUri = uri;
        Response response = null;
        do {
            final Request request = Requests.create(Method.HEAD, currentUri);
            response = client.handle(request);
            if (response.getStatus().isRedirection()) {
                currentUri = response.getLocationRef().toString();
                path.add(currentUri);
            }
            if (notExistingResource(response)) {
                throw new UriException(uri, response.getStatus());
            }
        } while (response.getStatus().isRedirection());
        return currentUri;
    }

    private boolean notExistingResource(final Response response) {
        return ERROR_STATUS.contains(response.getStatus());
    }

    protected List<String> path = Lists.newArrayList();

    private static final List<Status> ERROR_STATUS = Lists.newArrayList(Status.CONNECTOR_ERROR_COMMUNICATION,
            Status.CONNECTOR_ERROR_CONNECTION,
            Status.CLIENT_ERROR_NOT_FOUND,
            Status.CONNECTOR_ERROR_INTERNAL);

}
