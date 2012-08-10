package com.steambeat.domain.opinion;

import com.steambeat.domain.topic.Topic;
import com.steambeat.test.TestFactories;

public class JudgmentTestFactory {

    public Judgment newJudgment() {
        final Topic topic = TestFactories.topics().newTopic();
        return new Judgment(topic, Feeling.good);
    }

    public Judgment newBadJudgment() {
        final Topic topic = TestFactories.topics().newTopic();
        return new Judgment(topic, Feeling.bad);
    }

    public Judgment newGoodJudgment() {
        final Topic topic = TestFactories.topics().newTopic();
        return new Judgment(topic, Feeling.good);
    }
}
