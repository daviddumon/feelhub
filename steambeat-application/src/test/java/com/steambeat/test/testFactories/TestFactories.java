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

    public static StatisticsFactoryForTest statistics() {
        return new StatisticsFactoryForTest();
    }

    public static AlchemyFactoryForTest alchemy() {
        return new AlchemyFactoryForTest();
    }

    public static RelationFactoryForTest relations() {
        return new RelationFactoryForTest();
    }
}
