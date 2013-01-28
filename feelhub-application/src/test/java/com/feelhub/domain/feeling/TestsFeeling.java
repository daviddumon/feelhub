package com.feelhub.domain.feeling;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.google.common.eventbus.Subscribe;
import org.junit.*;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class TestsFeeling {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        user = TestFactories.users().createFakeActiveUser("mail@mail.com");
    }

    @Test
    public void canCreateWithTextAndUser() {
        final UUID id = UUID.randomUUID();

        final Feeling feeling = new Feeling(id, "hi!", user.getId());

        assertThat(feeling.getId()).isEqualTo(id);
        assertThat(feeling.getText()).isEqualTo("hi!");
        assertThat(feeling.getRawText()).isEqualTo("hi!");
        assertThat(feeling.getUserId()).isEqualTo(user.getId());
    }

    @Test
    public void canAddSentimentToFeeling() {
        final Feeling feeling = new Feeling(UUID.randomUUID(), "my feeling", user.getId());
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Sentiment sentiment = TestFactories.sentiments().newSentiment(realTopic, SentimentValue.good);

        feeling.addSentiment(sentiment);

        assertThat(feeling.getCreationDate()).isNotNull();
        assertThat(feeling.getCreationDate()).isEqualTo(time.getNow());
        assertThat(feeling.getLastModificationDate()).isEqualTo(time.getNow());
        assertThat(feeling.getSentiments().size()).isEqualTo(1);
        assertThat(feeling.getSentiments().get(0).getTopicId()).isEqualTo(realTopic.getId());
        assertThat(feeling.getSentiments().get(0).getSentimentValue()).isEqualTo(SentimentValue.good);
    }

    @Test
    public void addSentimentUpdateLastModificationDate() {
        final Feeling feeling = new Feeling(UUID.randomUUID(), "my feeling", user.getId());
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Sentiment sentiment = TestFactories.sentiments().newSentiment(realTopic, SentimentValue.good);
        time.waitDays(1);

        feeling.addSentiment(sentiment);

        assertThat(feeling.getLastModificationDate()).isEqualTo(time.getNow());
    }

    @Test
    public void canSpreadSentimentEvents() {
        final Feeling feeling = new Feeling(UUID.randomUUID(), "my feeling", user.getId());
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final SimpleSentimentListener simpleSentimentListener = mock(SimpleSentimentListener.class);
        DomainEventBus.INSTANCE.register(simpleSentimentListener);
        final Sentiment sentiment = TestFactories.sentiments().newSentiment(realTopic, SentimentValue.good);

        feeling.addSentiment(sentiment);

        final ArgumentCaptor<SentimentStatisticsEvent> captor = ArgumentCaptor.forClass(SentimentStatisticsEvent.class);
        verify(simpleSentimentListener, times(1)).handle(captor.capture());
        assertThat(captor.getValue()).isInstanceOf(SentimentStatisticsEvent.class);
        final SentimentStatisticsEvent event = captor.getAllValues().get(0);
        assertThat(event.getSentiment()).isEqualTo(feeling.getSentiments().get(0));
    }

    @Test
    public void hasALanguage() {
        final Feeling feeling = new Feeling(UUID.randomUUID(), "salut", user.getId());

        feeling.setLanguageCode("en");

        assertThat(feeling.getLanguageCode()).isEqualTo("en");
    }

    @Test
    public void sanitizeText() {
        final String text = "I enjoy +this but unless ##This!, i would love it ---#+=";

        final Feeling feeling = new Feeling(UUID.randomUUID(), text, user.getId());

        assertThat(feeling.getRawText()).isEqualTo(text);
        assertThat(feeling.getText()).isEqualTo("I enjoy this but unless This!, i would love it ");
    }

    @Test
    public void canSanitizeTextWithOnlySpacesAndSemanticMarkups() {
        final String text = " + -";

        final Feeling feeling = new Feeling(UUID.randomUUID(), text, user.getId());

        assertThat(feeling.getRawText()).isEqualTo(text);
        assertThat(feeling.getText()).isEqualTo("");
    }

    private class SimpleSentimentListener {

        @Subscribe
        public void handle(final SentimentStatisticsEvent sentimentStatisticsEvent) {

        }
    }

    private User user;
}
