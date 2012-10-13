package com.steambeat.domain.opinion;

import com.google.common.eventbus.Subscribe;
import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.user.User;
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

    @Before
    public void before() {
        activeUser = TestFactories.users().createFakeActiveUser("mail@mail.com");
    }

    @Test
    public void canCreateWithTextAndUser() {
        final Opinion opinion = new Opinion("salut", activeUser);

        assertThat(opinion.getText(), is("salut"));
        assertThat(opinion.getUserId(), is(activeUser.getId()));
        assertThat(opinion.getId(), notNullValue());
    }

    @Test
    public void canAddJudgementsToOpinion() {
        final Opinion opinion = new Opinion("my opinion", activeUser);
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
        final Opinion opinion = new Opinion("my opinion", activeUser);
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
        final Opinion opinion = new Opinion("my opinion", activeUser);
        final Reference reference = TestFactories.references().newReference();
        time.waitDays(1);

        opinion.addJudgment(reference, Feeling.good);

        assertThat(reference.getLastModificationDate(), is(time.getNow()));
    }

    @Test
    public void hasALanguage() {
        final Opinion opinion = new Opinion("salut", activeUser);

        opinion.setLanguageCode("en");

        assertThat(opinion.getLanguageCode(), is("en"));
    }

    private class SimpleJudgmentListener {

        @Subscribe
        public void handle(final JudgmentCreatedEvent judgmentCreatedEvent) {

        }
    }

    private User activeUser;
}
