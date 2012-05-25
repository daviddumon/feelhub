package com.steambeat.test;

import com.steambeat.domain.bingsearch.BingLink;
import com.steambeat.domain.subject.concept.Concept;

public class FakeBingLink extends BingLink{

    @Override
    public String getIllustration(final Concept concept) {
        return "";
    }
}
