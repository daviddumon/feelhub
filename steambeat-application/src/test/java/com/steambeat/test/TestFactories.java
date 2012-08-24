package com.steambeat.test;

import com.steambeat.domain.alchemy.*;
import com.steambeat.domain.eventbus.DomainEventFactoryForTest;
import com.steambeat.domain.illustration.IllustrationTestFactory;
import com.steambeat.domain.keyword.KeywordTestFactory;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.reference.ReferenceTestFactory;
import com.steambeat.domain.relation.RelationTestFactory;
import com.steambeat.domain.session.SessionTestFactory;
import com.steambeat.domain.statistics.StatisticsTestFactory;
import com.steambeat.domain.user.UserTestFactory;

public class TestFactories {

    private TestFactories() {
    }

    public static OpinionTestFactory opinions() {
        return new OpinionTestFactory();
    }

    public static JudgmentTestFactory judgments() {
        return new JudgmentTestFactory();
    }

    public static StatisticsTestFactory statistics() {
        return new StatisticsTestFactory();
    }

    public static NamedEntityTestFactory namedEntities() {
        return new NamedEntityTestFactory();
    }

    public static RelationTestFactory relations() {
        return new RelationTestFactory();
    }

    public static UserTestFactory users() {
        return new UserTestFactory();
    }

    public static SessionTestFactory sessions() {
        return new SessionTestFactory();
    }

    public static ReferenceTestFactory references() {
        return new ReferenceTestFactory();
    }

    public static KeywordTestFactory keywords() {
        return new KeywordTestFactory();
    }

    public static IllustrationTestFactory illustrations() {
        return new IllustrationTestFactory();
    }

    public static DomainEventFactoryForTest events() {
        return new DomainEventFactoryForTest();
    }

    public static AlchemyTestFactory alchemy() {
        return new AlchemyTestFactory();
    }
}
