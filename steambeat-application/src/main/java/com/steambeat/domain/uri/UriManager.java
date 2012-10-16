package com.steambeat.domain.uri;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.List;

public class UriManager {

    @Inject
    public UriManager(final UriResolver uriResolver) {
        this.uriResolver = uriResolver;
    }

    public List<String> getTokens(final String value) {
        final List<String> uris = uriResolver.resolve(value);
        return getTokensFor(uris);
    }

    private List<String> getTokensFor(final List<String> uris) {
        final List<String> result = Lists.newArrayList();
        for (final String uri : uris) {
            final UriTokenizer uriTokenizer = new UriTokenizer();
            result.addAll(uriTokenizer.getTokensFor(uri));
        }
        return result;
    }

    private final UriResolver uriResolver;
}
