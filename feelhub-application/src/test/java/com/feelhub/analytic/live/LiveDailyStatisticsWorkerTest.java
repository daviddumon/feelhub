package com.feelhub.analytic.live;

import com.feelhub.analytic.*;
import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.feeling.FeelingCreatedEvent;
import com.feelhub.domain.session.SessionCreatedEvent;
import com.feelhub.domain.topic.http.HttpTopicCreatedEvent;
import com.feelhub.domain.topic.real.RealTopicCreatedEvent;
import com.feelhub.domain.user.*;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class LiveDailyStatisticsWorkerTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void setUp() throws Exception {
        executor = mock(StatisticsCounterExecutor.class);
        worker = new LiveDailyStatisticsWorker(executor);
    }

    @Test
    public void canCountAccountCreation() {
        worker.onUserCreated(new UserCreatedEvent(TestFactories.users().createFakeUser("test@test.com")));

        StatisticsCounter counter = lastCounter();
        assertThat(counter.hasInc("signups")).isTrue();
    }

    @Test
    public void incrementLogInCount() {
        User user = TestFactories.users().createActiveUser("test@test.com");

        worker.onSessionCreated(new SessionCreatedEvent(UUID.randomUUID(), user.getId()));

        StatisticsCounter counter = lastCounter();
        assertThat(counter.hasInc("logins")).isTrue();
    }

    @Test
    public void incrementsHttpTopicCount() {
        worker.onHttpTopicCreated(new HttpTopicCreatedEvent(UUID.randomUUID(), null));

        StatisticsCounter counter = lastCounter();
        assertThat(counter.hasInc("httpTopics")).isTrue();
        assertThat(counter.hasInc("topics")).isTrue();
    }

    @Test
    public void incrementsRealTopicCount() {
        worker.onRealTopicCreated(new RealTopicCreatedEvent(UUID.randomUUID(), null));

        StatisticsCounter counter = lastCounter();
        assertThat(counter.hasInc("realTopics")).isTrue();
        assertThat(counter.hasInc("topics")).isTrue();
    }

    @Test
    public void incrementsFeelingCount() {
        worker.onFeelingCreated(new FeelingCreatedEvent(UUID.randomUUID(), UUID.randomUUID()));

        StatisticsCounter counter = lastCounter();
        assertThat(counter.hasInc("feelings")).isTrue();
    }

    private StatisticsCounter lastCounter() {
        ArgumentCaptor<StatisticsCounter> captor = ArgumentCaptor.forClass(StatisticsCounter.class);
        verify(executor).execute(captor.capture());
        return captor.getValue();
    }

    private LiveDailyStatisticsWorker worker;
    private StatisticsCounterExecutor executor;
}
