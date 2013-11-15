package com.feelhub.domain.feeling;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.real.*;
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
        return newFeeling(TestFactories.topics().newCompleteRealTopic().getId(), text);
    }

    public void newFeelings(final int quantity) {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        newFeelings(realTopic.getId(), quantity);
    }

    public List<Feeling> newFeelings(final UUID topicId, final int quantity) {
        final List<Feeling> result = Lists.newArrayList();
        for (int i = 0; i < quantity; i++) {
            final Feeling feeling = newFeeling(topicId, "i" + i);
            result.add(feeling);
        }
        return result;
    }

    public Feeling newFeeling(final UUID topicId, final String text) {
        final RealTopic realTopic = new RealTopic(topicId, RealTopicType.Automobile);
        return newFeeling(realTopic, text);
    }

    public Feeling newFeeling(final RealTopic topic, final String text) {
        final User activeUser = TestFactories.users().createFakeActiveUser("userforfeeling@mail.com");
        final Feeling feeling = new Feeling(activeUser.getId(), topic.getId());
        feeling.setText(text);
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        feeling.setFeelingValue(FeelingValue.happy);
        Repositories.feelings().add(feeling);
        return feeling;
    }

    public Feeling feelingWithUser(final UUID userId) {
        final User user = Repositories.users().get(userId);
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Feeling feeling = new Feeling(user.getId(), realTopic.getCurrentId());
        feeling.setText("text");
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        feeling.setFeelingValue(FeelingValue.happy);
        Repositories.feelings().add(feeling);
        return feeling;
    }

    public Feeling feelingWithAnUserATopicAndAFeelingValue(final User user, final Topic topic, final FeelingValue feelingValue) {
        final Feeling feeling = new Feeling(user.getId(), topic.getCurrentId());
        feeling.setText("text");
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        feeling.setFeelingValue(feelingValue);
        Repositories.feelings().add(feeling);
        return feeling;
    }

    public Feeling happyFeeling() {
        return feelingWithFeelingValue(FeelingValue.happy);
    }

    public Feeling sadFeeling() {
        return feelingWithFeelingValue(FeelingValue.sad);
    }

    public Feeling boredFeeling() {
        return feelingWithFeelingValue(FeelingValue.bored);
    }

    public Feeling happyFeeling(final Topic topic) {
        final Feeling feeling = feelingWithTopic(topic);
        feeling.setFeelingValue(FeelingValue.happy);
        return feeling;
    }

    public Feeling sadFeeling(final Topic topic) {
        final Feeling feeling = feelingWithTopic(topic);
        feeling.setFeelingValue(FeelingValue.sad);
        return feeling;
    }

    public Feeling boredFeeling(final Topic topic) {
        final Feeling feeling = feelingWithTopic(topic);
        feeling.setFeelingValue(FeelingValue.bored);
        return feeling;
    }

    public Feeling feelingWithFeelingValue(final FeelingValue feelingValue) {
        final Feeling feeling = feelingWithTopic(TestFactories.topics().newCompleteRealTopic());
        feeling.setFeelingValue(feelingValue);
        return feeling;
    }

    private Feeling feelingWithTopic(final Topic topic) {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final Feeling feeling = new Feeling(user.getId(), topic.getCurrentId());
        feeling.setText("text");
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        Repositories.feelings().add(feeling);
        return feeling;
    }
}
