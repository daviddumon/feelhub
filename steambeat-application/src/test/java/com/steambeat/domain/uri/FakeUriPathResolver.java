package com.steambeat.domain.uri;

import org.restlet.data.Status;

import java.util.List;

public class FakeUriPathResolver extends UriPathResolver {

    @Override
    public Path resolve(final Uri uri) {
        if (uri.equals(exceptionUri)) {
            throw new UriPathResolverException(uri, Status.CONNECTOR_ERROR_CONNECTION);
        }
        path.add(uri);
        if (!canonicalUri.isEmpty()) {
            path.add(canonicalUri);
        }
        return path;
    }

    public UriPathResolver thatFind(final Uri canonicalUri) {
        this.canonicalUri = canonicalUri;
        return this;
    }

    public void ThatThrow(final Uri exceptionUri) {
        this.exceptionUri = exceptionUri;
    }

    private Uri canonicalUri = Uri.empty();
    private Uri exceptionUri;
}
