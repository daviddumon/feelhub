package com.feelhub.domain.feeling;

import com.feelhub.domain.reference.Reference;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.test.TestFactories;
import com.google.common.collect.Lists;

import java.util.List;

public class FeelingTestFactory {

    public Feeling newFeeling() {
        return newFeelingWithText("text");
    }

    public Feeling newFeelingWithText(final String text) {
        return newFeeling(TestFactories.references().newReference(), text);
    }

    public void newFeelings(final int quantity) {
        final Reference reference = TestFactories.references().newReference();
        newFeelings(reference, quantity);
    }

    public List<Feeling> newFeelings(final Reference reference, final int quantity) {
        final List<Feeling> result = Lists.newArrayList();
        for (int i = 0; i < quantity; i++) {
            final Feeling feeling = newFeeling(reference, "i" + i);
            result.add(feeling);
        }
        return result;
    }

    public Feeling newFeeling(final Reference reference, final String text) {
        final User activeUser = TestFactories.users().createFakeActiveUser(text + "userforfeeling@mail.com");
        final Feeling feeling = new Feeling(text, activeUser.getId());
        feeling.addSentiment(reference, SentimentValue.bad);
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        Repositories.feelings().add(feeling);
        return feeling;
    }

    public Feeling newFeeling(final String text, final Sentiment sentiment) {
        final User activeUser = TestFactories.users().createFakeActiveUser("userforfeeling@mail.com");
        final Feeling feeling = new Feeling(text, activeUser.getId());
        feeling.addSentiment(sentiment);
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        Repositories.feelings().add(feeling);
        return feeling;
    }

    public Feeling newFeelingWithoutSentiments() {
        final User activeUser = TestFactories.users().createFakeActiveUser("userforfeeling@mail.com");
        final Feeling feeling = new Feeling("feeling without sentiment", activeUser.getId());
        feeling.setLanguageCode(FeelhubLanguage.reference().getCode());
        Repositories.feelings().add(feeling);
        return feeling;
    }
}
