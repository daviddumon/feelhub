package com.steambeat.test.testFactories;

public class TestFactories {

    private TestFactories() {
    }

    public static SubjectFactoryForTest subjects() {
        return new SubjectFactoryForTest();
    }

    public static OpinionFactoryForTest opinions() {
        return new OpinionFactoryForTest();
    }

    public static JudgmentFactoryForTest judgments() {
        return new JudgmentFactoryForTest();
    }

    public static AssociationFactoryForTest associations() {
        return new AssociationFactoryForTest();
    }

    public static WebPageStatFactoryForTest statistics() {
        return new WebPageStatFactoryForTest();
    }
}
