package com.steambeat.domain.opinion;

import com.google.common.eventbus.Subscribe;
import com.steambeat.domain.DomainEventBus;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.*;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
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
        final Subject subject = TestFactories.subjects().newWebPage();

        opinion.addJudgment(subject, Feeling.good);

        assertThat(opinion.getCreationDate(), notNullValue());
        assertThat(opinion.getCreationDate(), is(time.getNow()));
        assertThat(opinion.getJudgments().size(), is(1));
        assertThat(opinion.getJudgments().get(0).getSubject(), is(subject));
        assertThat(opinion.getJudgments().get(0).getFeeling(), is(Feeling.good));
    }

    @Test
    public void canSpreadJudgmentEvents() {
        final Opinion opinion = new Opinion("my opinion");
        final WebPage subject = TestFactories.subjects().newWebPage();
        final SimpleJudgmentListener judgmentEventListener = mock(SimpleJudgmentListener.class);
        DomainEventBus.INSTANCE.register(judgmentEventListener);

        opinion.addJudgment(subject, Feeling.good);

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
        final Subject subject = TestFactories.subjects().newWebPage();
        time.waitDays(1);

        opinion.addJudgment(subject, Feeling.good);

        assertThat(subject.getLastModificationDate(), is(time.getNow()));
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
