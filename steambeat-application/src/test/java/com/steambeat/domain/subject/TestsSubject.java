package com.steambeat.domain.subject;

import com.steambeat.domain.*;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.WithDomainEvent;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class TestsSubject {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        subject = getSubjectForTest();
    }

    @Test
    public void hasADefaultDate() {
        assertThat(subject.getCreationDate(), notNullValue());
    }

    @Test
    public void canAddOpinions() {
        final String text = "my opinion";

        final Opinion opinion = subject.createOpinion(text, Feeling.good);

        assertThat(opinion.getText(), is(text));
        assertThat(opinion.getFeeling(), is(Feeling.good));
        assertThat(opinion.getSubjectId(), is((Object) subject.getId()));
    }

    @Test
    public void canSpreadEvent() {
        DomainEventBus.INSTANCE.notifyOnSpread();
        final DomainEventListener eventListener = mock(DomainEventListener.class);
        DomainEventBus.INSTANCE.register(eventListener, OpinionPostedEvent.class);

        final Opinion opinion = subject.createOpinion("my opinion", Feeling.good);

        final ArgumentCaptor<DomainEvent> captor = ArgumentCaptor.forClass(DomainEvent.class);
        verify(eventListener).notify(captor.capture());
        assertThat(captor.getValue(), instanceOf(OpinionPostedEvent.class));
        final OpinionPostedEvent event = (OpinionPostedEvent) captor.getValue();
        assertThat(event.getSubject(), is(subject));
        assertThat(event.getOpinion(), is(opinion));
    }

    private WebPage getSubjectForTest() {
        return TestFactories.webPages().newWebPage();
    }

    private Subject subject;
}
