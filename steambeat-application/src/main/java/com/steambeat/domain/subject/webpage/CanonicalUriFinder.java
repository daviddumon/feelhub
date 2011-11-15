package com.steambeat.domain.subject.webpage;

import com.steambeat.tools.*;
import org.restlet.*;
import org.restlet.data.Method;

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
            if (response.getStatus().isError() && !response.getStatus().isServerError()) {
                throw new WebPageException(uri, response.getStatus());
            }
        } while (response.getStatus().isRedirection());
        return new Uri(currentUri);
    }

}
