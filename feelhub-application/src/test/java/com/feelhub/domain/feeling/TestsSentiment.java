package com.feelhub.domain.feeling;

import com.feelhub.domain.reference.Reference;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSentiment {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void hasAReferenceAndASentimentValue() {
        final Reference reference = TestFactories.references().newReference();
        final SentimentValue sentimentValue = SentimentValue.good;

        final Sentiment sentiment = new Sentiment(reference.getId(), sentimentValue);

        assertThat(sentiment, notNullValue());
        assertThat(sentiment.getReferenceId(), is(reference.getId()));
        assertThat(sentiment.getSentimentValue(), is(sentimentValue));
    }
}
