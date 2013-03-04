package com.feelhub.domain.feeling;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class SentimentStatisticsEventTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canPostASentimentEvent() {
        final Sentiment sentiment = TestFactories.sentiments().newSentiment();

        final SentimentStatisticsEvent sentimentStatisticsEvent = new SentimentStatisticsEvent(sentiment);

        assertThat(sentimentStatisticsEvent.getDate(), notNullValue());
        assertThat(sentimentStatisticsEvent.getDate(), is(time.getNow()));
        assertThat(sentimentStatisticsEvent.getSentiment(), is(sentiment));
    }
}
