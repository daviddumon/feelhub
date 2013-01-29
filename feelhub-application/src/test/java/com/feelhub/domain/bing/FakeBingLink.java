package com.feelhub.domain.bing;

import com.feelhub.domain.admin.AdminStatisticCallsCounter;
import com.google.common.collect.Lists;

import java.util.List;

public class FakeBingLink extends BingLink {

    public FakeBingLink() {
        super(new AdminStatisticCallsCounter());
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
