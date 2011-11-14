package com.steambeat.domain.subject.feed;

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
            System.out.println("ERREUR");
            throw new FeedException(e);
        } finally {
            Clients.stop(client);
        }
    }

    private Uri doFollow(Uri uri, Client client) {
        String currentUri = uri.toString();
        Response response = null;
        do {
            final Request request = Requests.create(Method.HEAD, currentUri);
            response = client.handle(request);
            if (response.getStatus().isRedirection()) {
                currentUri = response.getLocationRef().toString();
            }
            if (response.getStatus().isError() && !response.getStatus().isServerError()) {
                throw new FeedException(uri, response.getStatus());
            }
        } while (response.getStatus().isRedirection());
        return new Uri(currentUri);
    }

}
