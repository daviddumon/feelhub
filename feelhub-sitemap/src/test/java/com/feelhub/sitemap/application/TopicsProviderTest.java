package com.feelhub.sitemap.application;

import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.sitemap.test.TestWithMongo;
import com.feelhub.test.TestFactories;
import org.junit.Test;
import org.mongolink.MongoSession;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class TopicsProviderTest extends TestWithMongo {

    @Test
    public void canGetTopics() {
        MongoSession session = newSession();
        session.start();
        TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();
        session.stop();

        List<RealTopic> topics = new TopicsProvider().topics(newSession());

        assertThat(topics).hasSize(3);
    }
}
