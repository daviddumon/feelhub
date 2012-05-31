package com.steambeat.test;

import com.steambeat.domain.association.tag.Tag;
import com.steambeat.domain.bingsearch.BingLink;

public class FakeBingLink extends BingLink {

    @Override
    public String getIllustration(final Tag tag) {
        return "fake";
    }
}
