package com.feelhub.domain.topic.http.uri;

import com.feelhub.tools.*;
import com.google.common.collect.Lists;
import org.restlet.*;
import org.restlet.data.*;

import java.util.List;

public class UriResolver {

    public ResolverResult resolve(final Uri uri) {
        final ResolverResult resolverResult = new ResolverResult();
        resolverResult.addUriToPath(uri);
        followRedirection(uri, resolverResult);
        return resolverResult;
    }

    private String followRedirection(final Uri uri, final ResolverResult resolverResult) {
        final Client client = Clients.create();
        try {
            return doFollow(uri, client, resolverResult);
        } catch (Exception e) {
            throw new UriException(e);
        } finally {
            Clients.stop(client);
        }
    }

    private String doFollow(final Uri uri, final Client client, final ResolverResult resolverResult) {
        String currentUri = uri.getValue();
        Response response = null;
        do {
            final Request request = Requests.create(Method.HEAD, currentUri);
            response = client.handle(request);
            if (response.getStatus().isRedirection()) {
                currentUri = response.getLocationRef().toString();
                resolverResult.addUriToPath(new Uri(currentUri));
            }
            if (notExistingResource(response)) {
                throw new UriException(uri.getValue(), response.getStatus());
            }
            resolverResult.setMediaType(response.getEntity().getMediaType());
        } while (response.getStatus().isRedirection());
        return currentUri;
    }

    private boolean notExistingResource(final Response response) {
        return ERROR_STATUS.contains(response.getStatus());
    }

    private static final List<Status> ERROR_STATUS = Lists.newArrayList(Status.CONNECTOR_ERROR_COMMUNICATION,
            Status.CONNECTOR_ERROR_CONNECTION,
            Status.CLIENT_ERROR_NOT_FOUND,
            Status.CONNECTOR_ERROR_INTERNAL);

}
