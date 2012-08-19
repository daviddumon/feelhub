package com.steambeat.domain.bingsearch;

import com.steambeat.domain.keyword.Keyword;

public class FakeBingLink extends BingLink {

    @Override
    public String getIllustration(final Keyword keyword) {
        return keyword.getValue() + "link";
    }
}
