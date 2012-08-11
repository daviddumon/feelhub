package com.steambeat.test;

import com.steambeat.domain.bingsearch.BingLink;
import com.steambeat.domain.keyword.Keyword;

public class FakeBingLink extends BingLink {

    @Override
    public String getIllustration(final Keyword tag) {
        return "fake";
    }
}
