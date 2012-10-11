package com.steambeat.application;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import org.junit.*;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class TestsOpinionService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Test
    public void canAddOpinionAndJudgements() {
        final OpinionService service = new OpinionService();
        final Reference reference = TestFactories.references().newReference();
        final Judgment judgment = new Judgment(reference.getId(), Feeling.good);

        service.addOpinion("Le texte de l'opinion", Lists.newArrayList(judgment), "en");

        assertThat(Repositories.opinions().getAll().size(), is(1));
        final Opinion opinion = Repositories.opinions().getAll().get(0);
        assertThat(opinion.getText(), is("Le texte de l'opinion"));
        assertThat(opinion.getJudgments().size(), is(1));
        assertThat(opinion.getJudgments().get(0), is(judgment));
        assertThat(opinion.getLanguageCode(), is("en"));
    }

    @Test
    public void canSpreadOpinionEvent() {
        final OpinionService service = new OpinionService();
        final Judgment judgment = new Judgment(TestFactories.references().newReference().getId(), Feeling.good);
        final SimpleOpinionListener opinionEventListener = mock(SimpleOpinionListener.class);
        DomainEventBus.INSTANCE.register(opinionEventListener);

        final Opinion opinion = service.addOpinion("Le texte de l'opinion", Lists.newArrayList(judgment), "en");

        final ArgumentCaptor<OpinionCreatedEvent> captor = ArgumentCaptor.forClass(OpinionCreatedEvent.class);
        verify(opinionEventListener).handle(captor.capture());
        assertThat(captor.getValue(), instanceOf(OpinionCreatedEvent.class));
        final OpinionCreatedEvent event = captor.getValue();
        assertThat(event.getOpinion(), is(opinion));
    }

    private class SimpleOpinionListener {

        @Subscribe
        public void handle(final OpinionCreatedEvent opinionCreatedEvent) {

        }
    }
}
