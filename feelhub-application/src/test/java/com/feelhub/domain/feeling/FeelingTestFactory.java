package com.feelhub.domain.feeling;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.test.TestFactories;
import com.google.common.collect.Lists;

import java.util.*;

public class FeelingTestFactory {

    public Feeling newFeeling() {
        return newFeelingWithText("text");
    }

    public Feeling newFeelingWithText(final String text) {
        return newFeeling(TestFactories.topics().newTopic().getId(), text);
    }

    public void newFeelings(final int quantity) {
        final Topic topic = TestFactories.topics().newTopic();
        newFeelings(topic.getId(), quantity);
    }

    public List<Feeling> newFeelings(final UUID topicId, final int quantity) {
        final List<Feeling> result = Lists.newArrayList();
        for (int i = 0; i < quantity; i++) {
            final Feeling feeling = newFeeling(topicId, "i" + i);
            result.add(feeling);
        }
        return result;
    }

    public Feeling newFeeling(final String text, final Sentiment sentiment) {
        final User activeUser = TestFactories.users().createFakeActiveUser("userforfeeling@mail.com");
        final Feeling feeling = new Feeling(text, activeUser);
        feeling.addSentiment(sentiment);
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        Repositories.feelings().add(feeling);
        return feeling;
    }

    public Feeling newFeeling(final UUID topicId, final String text) {
        final User activeUser = TestFactories.users().createFakeActiveUser(text + "userforfeeling@mail.com");
        final Feeling feeling = new Feeling(text, activeUser);
        feeling.addSentiment(new Topic(topicId), SentimentValue.bad);
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        Repositories.feelings().add(feeling);
        return feeling;
    }

    public Feeling newFeelingWithoutSentiments() {
        final User activeUser = TestFactories.users().createFakeActiveUser("userforfeeling@mail.com");
        final Feeling feeling = new Feeling("feeling without sentiment", activeUser);
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        Repositories.feelings().add(feeling);
        return feeling;
    }
}
