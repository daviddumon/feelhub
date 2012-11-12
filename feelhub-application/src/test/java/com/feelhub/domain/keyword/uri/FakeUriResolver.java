package com.feelhub.domain.keyword.uri;

import com.google.common.collect.Lists;
import org.restlet.data.Status;

import java.util.List;

public class FakeUriResolver extends UriResolver {

    @Override
    public List<String> resolve(final String uri) {
        final List<String> path = Lists.newArrayList();
        if (uri.equals(exceptionUri)) {
            throw new UriException(uri, Status.CONNECTOR_ERROR_CONNECTION);
        }
        path.add(uri);
        if (!canonicalUri.isEmpty()) {
            path.add(canonicalUri);
        }
        return path;
    }

    public UriResolver thatFind(final String canonicalUri) {
        this.canonicalUri = canonicalUri;
        return this;
    }

    public UriResolver ThatThrow(final String exceptionUri) {
        this.exceptionUri = exceptionUri;
        return this;
    }

    private String canonicalUri = "";
    private String exceptionUri = "http://www.exception.com";
}
