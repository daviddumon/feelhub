package com.feelhub.domain.feeling;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.google.common.eventbus.Subscribe;
import org.junit.*;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
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
        activeUser = TestFactories.users().createFakeActiveUser("mail@mail.com");
    }

    @Test
    public void canCreateWithTextAndUser() {
        final Feeling feeling = new Feeling("salut", activeUser.getId());

        assertThat(feeling.getText(), is("salut"));
        assertThat(feeling.getUserId(), is(activeUser.getId()));
        assertThat(feeling.getId(), notNullValue());
    }

    @Test
    public void canAddSentimentsToFeeling() {
        final Feeling feeling = new Feeling("my feeling", activeUser.getId());
        final Topic topic = TestFactories.topics().newTopic();

        feeling.addSentiment(topic, SentimentValue.good);

        assertThat(feeling.getCreationDate(), notNullValue());
        assertThat(feeling.getCreationDate(), is(time.getNow()));
        assertThat(feeling.getSentiments().size(), is(1));
        assertThat(feeling.getSentiments().get(0).getTopicId(), is(topic.getId()));
        assertThat(feeling.getSentiments().get(0).getSentimentValue(), is(SentimentValue.good));
    }

    @Test
    public void canSpreadSentimentEvents() {
        final Feeling feeling = new Feeling("my feeling", activeUser.getId());
        final Topic topic = TestFactories.topics().newTopic();
        final SimpleSentimentListener simpleSentimentListener = mock(SimpleSentimentListener.class);
        DomainEventBus.INSTANCE.register(simpleSentimentListener);

        feeling.addSentiment(topic, SentimentValue.good);

        final ArgumentCaptor<SentimentStatisticsEvent> captor = ArgumentCaptor.forClass(SentimentStatisticsEvent.class);
        verify(simpleSentimentListener, times(1)).handle(captor.capture());
        assertThat(captor.getValue(), instanceOf(SentimentStatisticsEvent.class));
        final SentimentStatisticsEvent event = captor.getAllValues().get(0);
        assertThat(event.getSentiment(), is(feeling.getSentiments().get(0)));
    }

    @Test
    public void setLastModificationDateOnSentimentCreation() {
        final Feeling feeling = new Feeling("my feeling", activeUser.getId());
        final Topic topic = TestFactories.topics().newTopic();
        time.waitDays(1);

        feeling.addSentiment(topic, SentimentValue.good);

        assertThat(topic.getLastModificationDate(), is(time.getNow()));
    }

    @Test
    public void hasALanguage() {
        final Feeling feeling = new Feeling("salut", activeUser.getId());

        feeling.setLanguageCode("en");

        assertThat(feeling.getLanguageCode(), is("en"));
    }

    private class SimpleSentimentListener {

        @Subscribe
        public void handle(final SentimentStatisticsEvent sentimentStatisticsEvent) {

        }
    }

    private User activeUser;
}
