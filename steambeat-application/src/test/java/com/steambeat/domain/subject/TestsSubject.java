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
    public void canAddJudgment() {
        final Feeling feeling = Feeling.good;

        final Judgment judgment = subject.createJudgment(feeling);

        assertThat(judgment.getFeeling(), is(Feeling.good));
        assertThat(judgment.getSubject(), is((Object) subject));
    }

    @Test
    public void canSpreadJudgmentEvent() {
        DomainEventBus.INSTANCE.notifyOnSpread();
        final DomainEventListener eventListener = mock(DomainEventListener.class);
        DomainEventBus.INSTANCE.register(eventListener, JudgmentPostedEvent.class);

        final Judgment judgment = subject.createJudgment(Feeling.good);

        final ArgumentCaptor<DomainEvent> captor = ArgumentCaptor.forClass(DomainEvent.class);
        verify(eventListener).notify(captor.capture());
        assertThat(captor.getValue(), instanceOf(JudgmentPostedEvent.class));
        final JudgmentPostedEvent event = (JudgmentPostedEvent) captor.getValue();
        assertThat(event.getJudgment(), is(judgment));
    }

    private WebPage getSubjectForTest() {
        return TestFactories.webPages().newWebPage();
    }

    private Subject subject;
}
