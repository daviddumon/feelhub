package com.steambeat.domain.subject;

import com.steambeat.domain.subject.feed.Feed;
import com.steambeat.domain.*;
import com.steambeat.test.WithDomainEvent;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.domain.opinion.*;
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
    public WithDomainEvent e = new WithDomainEvent();

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
        final String value1 = "opinion1";

        final Opinion opinion1 = subject.createOpinion(value1, Feeling.good);

        assertThat(opinion1.getText(), is(value1));
        assertThat(opinion1.getFeeling(), is(Feeling.good));
        assertThat(opinion1.getSubjectId(), is((Object) subject.getId()));
    }

    @Test
    public void canSpreadEvent() {
        DomainEventBus.INSTANCE.notifyOnSpread();
        final DomainEventListener eventListener = mock(DomainEventListener.class);
        DomainEventBus.INSTANCE.register(eventListener, OpinionPostedEvent.class);

        final Opinion opinion = subject.createOpinion("test", Feeling.good);

        ArgumentCaptor<DomainEvent> captor = ArgumentCaptor.forClass(DomainEvent.class);
        verify(eventListener).notify(captor.capture());
        assertThat(captor.getValue(), instanceOf(OpinionPostedEvent.class));
        OpinionPostedEvent event = (OpinionPostedEvent) captor.getValue();
        assertThat(event.getSubject(), is((Subject) subject));
        assertThat(event.getOpinion(), is(opinion));
    }

    private Feed getSubjectForTest() {
        return new Feed(TestFactories.associations().newAssociation("http://feed/test.com", "http://feed/test.com"));
    }

    private Subject subject;
}
