package com.steambeat.domain.bingsearch;

public class FakeBingLink extends BingLink {

    @Override
    public String getIllustration(final String value) {
        return value + "link";
    }
}
