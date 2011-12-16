package com.steambeat.test.testFactories;

import com.steambeat.test.StubCanonicalUriFinder;

public class TestFactories {

    private TestFactories() {
    }

    public static WebPageFactoryForTest webPages() {
        return new WebPageFactoryForTest();
    }

    public static AssociationFactoryForTest associations() {
        return new AssociationFactoryForTest();
    }

    public static WebPageStatFactoryForTest statistics() {
        return new WebPageStatFactoryForTest();
    }

    public static StubCanonicalUriFinder canonicalUriFinder() {
        return new StubCanonicalUriFinder();
    }

    public static OpinionFactoryForTest opinions() {
        return new OpinionFactoryForTest();
    }

    public static JudgmentFactoryForTest judgments() {
        return new JudgmentFactoryForTest();
    }
}
