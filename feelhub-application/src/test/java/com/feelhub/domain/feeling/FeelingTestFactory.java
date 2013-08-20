package com.feelhub.domain.feeling;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.test.TestFactories;
import com.google.common.collect.Lists;

import java.util.*;

//todo a refacto completement
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

    public Feeling newFeeling(final String text, final Sentiment sentiment) {
        final User activeUser = TestFactories.users().createFakeActiveUser("userforfeeling@mail.com");
        final Feeling feeling = new Feeling(activeUser.getId(), null);
        feeling.setText(text);
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        Repositories.feelings().add(feeling);
        return feeling;
    }

    public Feeling newFeeling(final UUID topicId, final String text) {
        final RealTopic realTopic = new RealTopic(topicId, RealTopicType.Automobile);
        return newFeeling(realTopic, text);
    }

    public Feeling newFeeling(final RealTopic topic, final String text) {
        return newFeeling(topic, text, SentimentValue.bad);
    }

    public Feeling newFeeling(final RealTopic topic, final String text, final SentimentValue sentimentValue) {
        final User activeUser = TestFactories.users().createFakeActiveUser("userforfeeling@mail.com");
        final Feeling feeling = new Feeling(activeUser.getId(), topic.getId());
        feeling.setText(text);
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        Repositories.feelings().add(feeling);
        return feeling;
    }

    public Feeling newFeelingWithoutSentiments() {
        final User activeUser = TestFactories.users().createFakeActiveUser("userforfeeling@mail.com");
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Feeling feeling = new Feeling(activeUser.getId(), realTopic.getCurrentId());
        feeling.setText("feeling without sentiment");
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        Repositories.feelings().add(feeling);
        return feeling;
    }

    public Feeling newFeeling(final UUID userId) {
        final User user = Repositories.users().get(userId);
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Feeling feeling = new Feeling(user.getId(), realTopic.getCurrentId());
        feeling.setText("text");
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        Repositories.feelings().add(feeling);
        return feeling;
    }

    public Feeling newEmptyFeeling(final String text) {
        final User activeUser = TestFactories.users().createFakeActiveUser("userforfeeling@mail.com");
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Feeling feeling = new Feeling(activeUser.getId(), realTopic.getCurrentId());
        feeling.setText(text);
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        Repositories.feelings().add(feeling);
        return feeling;
    }

    public Feeling goodFeeling() {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Feeling feeling = new Feeling(user.getId(), realTopic.getCurrentId());
        feeling.setText("text");
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        feeling.setFeelingValue(FeelingValue.good);
        Repositories.feelings().add(feeling);
        return feeling;
    }

    public Feeling badFeeling() {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Feeling feeling = new Feeling(user.getId(), realTopic.getCurrentId());
        feeling.setText("text");
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        feeling.setFeelingValue(FeelingValue.bad);
        Repositories.feelings().add(feeling);
        return feeling;
    }

    public Feeling neutralFeeling() {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Feeling feeling = new Feeling(user.getId(), realTopic.getCurrentId());
        feeling.setText("text");
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        feeling.setFeelingValue(FeelingValue.neutral);
        Repositories.feelings().add(feeling);
        return feeling;
    }

    public Feeling goodFeeling(final Topic topic) {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final Feeling feeling = new Feeling(user.getId(), topic.getCurrentId());
        feeling.setText("text");
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        feeling.setFeelingValue(FeelingValue.good);
        Repositories.feelings().add(feeling);
        return feeling;
    }

    public Feeling badFeeling(final Topic topic) {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final Feeling feeling = new Feeling(user.getId(), topic.getCurrentId());
        feeling.setText("text");
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        feeling.setFeelingValue(FeelingValue.bad);
        Repositories.feelings().add(feeling);
        return feeling;
    }

    public Feeling neutralFeeling(final Topic topic) {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final Feeling feeling = new Feeling(user.getId(), topic.getCurrentId());
        feeling.setText("text");
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        feeling.setFeelingValue(FeelingValue.neutral);
        Repositories.feelings().add(feeling);
        return feeling;
    }

    public Feeling feelingWithValue(final FeelingValue feelingValue) {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final Feeling feeling = new Feeling(user.getId(), TestFactories.topics().newCompleteRealTopic().getCurrentId());
        feeling.setText("text");
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        feeling.setFeelingValue(feelingValue);
        Repositories.feelings().add(feeling);
        return feeling;
    }
}
