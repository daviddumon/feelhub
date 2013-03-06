package com.feelhub.domain.feeling;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class SentimentAddedEventTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canPostASentimentEvent() {
        final Sentiment sentiment = TestFactories.sentiments().newSentiment();

        final SentimentAddedEvent sentimentAddedEvent = new SentimentAddedEvent(sentiment);

        assertThat(sentimentAddedEvent.getDate(), notNullValue());
        assertThat(sentimentAddedEvent.getDate(), is(time.getNow()));
        assertThat(sentimentAddedEvent.getSentiment(), is(sentiment));
    }
}
