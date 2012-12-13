package com.feelhub.domain.bingsearch;

import com.feelhub.domain.tag.uri.FakeUriResolver;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.List;

public class FakeBingLink extends BingLink {

    @Inject
    public FakeBingLink(final FakeUriResolver uriResolver) {
        super(uriResolver);
    }

    @Override
    public List<String> getIllustrations(final String value, final String type) {
        final List<String> results = Lists.newArrayList();
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(value);
        if (!type.isEmpty()) {
            stringBuilder.append(" ");
            stringBuilder.append(type);
        }
        stringBuilder.append("link");
        results.add(stringBuilder.toString());
        return results;
    }
}
