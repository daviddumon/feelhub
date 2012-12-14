package com.feelhub.domain.topic.http.uri;

import org.restlet.data.*;

public class FakeUriResolver extends UriResolver {

    @Override
    public ResolverResult resolve(final Uri uri) {
        final ResolverResult resolverResult = new ResolverResult();
        if (uri.equals(exceptionUri)) {
            throw new UriException(uri.getValue(), Status.CONNECTOR_ERROR_CONNECTION);
        }
        resolverResult.addUriToPath(uri);
        if (!canonicalUri.isEmpty()) {
            resolverResult.addUriToPath(new Uri(canonicalUri));
        }
        if (mediaType != null) {
            resolverResult.setMediaType(mediaType);
        } else {
            resolverResult.setMediaType(MediaType.TEXT_HTML);
        }
        return resolverResult;
    }

    public UriResolver thatFind(final String canonicalUri) {
        this.canonicalUri = canonicalUri;
        return this;
    }

    public UriResolver ThatThrow(final String exceptionUri) {
        this.exceptionUri = exceptionUri;
        return this;
    }

    public void thatFind(final MediaType mediaType) {
        this.mediaType = mediaType;
    }

    private String canonicalUri = "";
    private String exceptionUri = "http://www.exception.com";
    private MediaType mediaType;
}
