package com.steambeat.test.testFactories;

import com.steambeat.test.StubCanonicalUriFinder;

public class TestFactories {

    private TestFactories() {
    }

    public static FeedFactoryForTest feeds() {
        return new FeedFactoryForTest();
    }

    public static AssociationFactoryForTest associations() {
        return new AssociationFactoryForTest();
    }

    public static FeedStatFactoryForTest statistics() {
        return new FeedStatFactoryForTest();
    }

    public static StubCanonicalUriFinder canonicalUriFinder() {
        return new StubCanonicalUriFinder();
    }

    public static OpinionFactoryForTest opinions() {
        return new OpinionFactoryForTest();
    }
}
