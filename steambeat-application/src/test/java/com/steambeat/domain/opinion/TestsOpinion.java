package com.steambeat.domain.opinion;

import com.google.common.eventbus.Subscribe;
import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.topic.Topic;
import com.steambeat.test.SystemTime;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import org.junit.*;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class TestsOpinion {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Test
    public void canCreateWithText() {
        final String text = "my opinion";

        final Opinion opinion = new Opinion(text);

        assertThat(opinion.getText(), is(text));
    }

    @Test
    public void canAddJudgementsToOpinion() {
        final Opinion opinion = new Opinion("my opinion");
        final Topic topic = TestFactories.topics().newTopic();

        opinion.addJudgment(topic, Feeling.good);

        assertThat(opinion.getCreationDate(), notNullValue());
        assertThat(opinion.getCreationDate(), is(time.getNow()));
        assertThat(opinion.getJudgments().size(), is(1));
        assertThat(opinion.getJudgments().get(0).getTopic(), is(topic));
        assertThat(opinion.getJudgments().get(0).getFeeling(), is(Feeling.good));
    }

    @Test
    public void canSpreadJudgmentEvents() {
        final Opinion opinion = new Opinion("my opinion");
        final Topic topic = TestFactories.topics().newTopic();
        final SimpleJudgmentListener judgmentEventListener = mock(SimpleJudgmentListener.class);
        DomainEventBus.INSTANCE.register(judgmentEventListener);

        opinion.addJudgment(topic, Feeling.good);

        final ArgumentCaptor<JudgmentPostedEvent> captor = ArgumentCaptor.forClass(JudgmentPostedEvent.class);
        verify(judgmentEventListener, times(1)).handle(captor.capture());
        assertThat(captor.getValue(), instanceOf(JudgmentPostedEvent.class));
        final JudgmentPostedEvent event = captor.getAllValues().get(0);
        assertThat(event.getJudgment(), is(opinion.getJudgments().get(0)));
    }

    @Test
    public void canSpreadOpinionEvent() {
        final SimpleOpinionListener opinionEventListener = mock(SimpleOpinionListener.class);
        DomainEventBus.INSTANCE.register(opinionEventListener);

        final Opinion opinion = new Opinion("my opinion");

        final ArgumentCaptor<OpinionPostedEvent> captor = ArgumentCaptor.forClass(OpinionPostedEvent.class);
        verify(opinionEventListener).handle(captor.capture());
        assertThat(captor.getValue(), instanceOf(OpinionPostedEvent.class));
        final OpinionPostedEvent event = captor.getValue();
        assertThat(event.getOpinion(), is(opinion));
    }

    @Test
    public void setLastModificationDateOnJudgmentCreation() {
        final Opinion opinion = new Opinion("my opinion");
        final Topic topic = TestFactories.topics().newTopic();
        time.waitDays(1);

        opinion.addJudgment(topic, Feeling.good);

        assertThat(topic.getLastModificationDate(), is(time.getNow()));
    }

    private class SimpleJudgmentListener {

        @Subscribe
        public void handle(JudgmentPostedEvent judgmentPostedEvent) {

        }
    }

    private class SimpleOpinionListener {

        @Subscribe
        public void handle(OpinionPostedEvent opinionPostedEvent) {

        }
    }

}
