package com.feelhub.domain.bing;

import com.google.common.collect.Lists;

import java.util.List;

public class FakeBingLink extends BingLink {

    @Override
    public List<String> getIllustrations(final String value) {
        final List<String> results = Lists.newArrayList();
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(value);
        stringBuilder.append("link");
        results.add(stringBuilder.toString());
        return results;
    }
}
