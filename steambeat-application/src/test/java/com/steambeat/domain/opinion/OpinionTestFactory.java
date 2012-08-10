package com.steambeat.domain.opinion;

import com.steambeat.domain.topic.Topic;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.TestFactories;

public class OpinionTestFactory {

    public Opinion newOpinion() {
        return newOpinionWithText("my good opinion");
    }

    public Opinion newOpinionWithText(final String text) {
        return newOpinion(TestFactories.topics().newTopic(), text);
    }

    public void newOpinions(final int quantity) {
        final Topic topic = TestFactories.topics().newTopic();
        newOpinions(topic, quantity);
    }

    public void newOpinions(final Topic topic, final int quantity) {
        for (int i = 0; i < quantity; i++) {
            newOpinion(topic, "i" + i);
        }
    }

    private Opinion newOpinion(final Topic topic, final String text) {
        final Opinion opinion = new Opinion(text);
        opinion.addJudgment(topic, Feeling.bad);
        Repositories.opinions().add(opinion);
        return opinion;
    }
}
