package com.feelhub.domain.bingsearch;

import com.google.common.collect.Lists;

import java.util.List;

public class FakeBingLink extends BingLink {

    @Override
    public List<String> getIllustrations(final String value, final String type) {
        List<String> results = Lists.newArrayList();
        StringBuilder stringBuilder = new StringBuilder();
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
