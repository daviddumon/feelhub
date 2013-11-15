package com.feelhub.sitemap.application;

import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.sitemap.test.TestWithMongo;
import com.feelhub.test.TestFactories;
import org.junit.Test;
import org.mongolink.MongoSession;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TopicsProviderTest extends TestWithMongo {

    @Test
    public void canGetTopics() {
        final MongoSession session = newSession();
        session.start();
        TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();
        session.stop();

        final List<Topic> topics = new TopicsProvider().topics(newSession());

        assertThat(topics).hasSize(0);
    }

    @Test
    public void onlyIndexesTopicsWithOneFeeling() {
        final MongoSession session = newSession();
        session.start();
        final RealTopic topicA = TestFactories.topics().newCompleteRealTopic();
        final RealTopic topicB = TestFactories.topics().newCompleteRealTopic();
        final RealTopic topicC = TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();
        topicA.increasesFeelingCount(TestFactories.feelings().sadFeeling());
        topicB.increasesFeelingCount(TestFactories.feelings().happyFeeling());
        topicC.increasesFeelingCount(TestFactories.feelings().boredFeeling());
        session.stop();

        final List<Topic> topics = new TopicsProvider().topics(newSession());

        assertThat(topics).hasSize(3);
    }
}
