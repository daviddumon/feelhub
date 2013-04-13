package com.feelhub.test;

import com.feelhub.domain.alchemy.*;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.related.RelatedTestFactory;
import com.feelhub.domain.session.SessionTestFactory;
import com.feelhub.domain.statistics.StatisticsTestFactory;
import com.feelhub.domain.tag.TagTestFactory;
import com.feelhub.domain.topic.TopicTestFactory;
import com.feelhub.domain.user.UserTestFactory;

public class TestFactories {

    private TestFactories() {
    }

    public static FeelingTestFactory feelings() {
        return new FeelingTestFactory();
    }

    public static SentimentTestFactory sentiments() {
        return new SentimentTestFactory();
    }

    public static StatisticsTestFactory statistics() {
        return new StatisticsTestFactory();
    }

    public static NamedEntityTestFactory namedEntities() {
        return new NamedEntityTestFactory();
    }

    public static RelatedTestFactory related() {
        return new RelatedTestFactory();
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

    public static TagTestFactory tags() {
        return new TagTestFactory();
    }

    public static AlchemyTestFactory alchemy() {
        return new AlchemyTestFactory();
    }

}
