package com.steambeat.domain.opinion;

import com.google.common.eventbus.Subscribe;
import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.*;
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
        final Reference reference = TestFactories.references().newReference();

        opinion.addJudgment(reference, Feeling.good);

        assertThat(opinion.getCreationDate(), notNullValue());
        assertThat(opinion.getCreationDate(), is(time.getNow()));
        assertThat(opinion.getJudgments().size(), is(1));
        assertThat(opinion.getJudgments().get(0).getReferenceId(), is(reference.getId()));
        assertThat(opinion.getJudgments().get(0).getFeeling(), is(Feeling.good));
    }

    @Test
    public void canSpreadJudgmentEvents() {
        final Opinion opinion = new Opinion("my opinion");
        final Reference reference = TestFactories.references().newReference();
        final SimpleJudgmentListener judgmentEventListener = mock(SimpleJudgmentListener.class);
        DomainEventBus.INSTANCE.register(judgmentEventListener);

        opinion.addJudgment(reference, Feeling.good);

        final ArgumentCaptor<JudgmentCreatedEvent> captor = ArgumentCaptor.forClass(JudgmentCreatedEvent.class);
        verify(judgmentEventListener, times(1)).handle(captor.capture());
        assertThat(captor.getValue(), instanceOf(JudgmentCreatedEvent.class));
        final JudgmentCreatedEvent event = captor.getAllValues().get(0);
        assertThat(event.getJudgment(), is(opinion.getJudgments().get(0)));
    }

    @Test
    public void setLastModificationDateOnJudgmentCreation() {
        final Opinion opinion = new Opinion("my opinion");
        final Reference reference = TestFactories.references().newReference();
        time.waitDays(1);

        opinion.addJudgment(reference, Feeling.good);

        assertThat(reference.getLastModificationDate(), is(time.getNow()));
    }

    private class SimpleJudgmentListener {

        @Subscribe
        public void handle(final JudgmentCreatedEvent judgmentCreatedEvent) {

        }
    }
}
