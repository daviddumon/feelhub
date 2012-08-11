package com.steambeat.test;

import com.steambeat.domain.alchemy.AlchemyTestFactory;
import com.steambeat.domain.keyword.KeywordTestFactory;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.relation.RelationTestFactory;
import com.steambeat.domain.session.SessionTestFactory;
import com.steambeat.domain.statistics.StatisticsTestFactory;
import com.steambeat.domain.topic.TopicTestFactory;
import com.steambeat.domain.uri.UriTestFactory;
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

    public static AlchemyTestFactory alchemy() {
        return new AlchemyTestFactory();
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

    public static TopicTestFactory topics() {
        return new TopicTestFactory();
    }

    public static UriTestFactory uris() {
        return new UriTestFactory();
    }

    public static KeywordTestFactory keywords() {
        return new KeywordTestFactory();
    }
}
