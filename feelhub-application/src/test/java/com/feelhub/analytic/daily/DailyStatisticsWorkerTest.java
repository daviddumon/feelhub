package com.feelhub.analytic.daily;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.session.SessionCreatedEvent;
import com.feelhub.domain.user.User;
import com.feelhub.domain.user.UserCreatedEvent;
import com.feelhub.domain.user.activation.UserActivatedEvent;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class DailyStatisticsWorkerTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void setUp() throws Exception {
        stat = mock(DailyUserStatisticsCounter.class);
        worker = new DailyStatisticsWorker(stat);
    }

    @Test
    public void canCountAccountCreation() {
        worker.onUserCreated(new UserCreatedEvent(TestFactories.users().createFakeUser("test@test.com")));

        verify(stat).incrementUserCreation();
    }

    @Test
    public void incrementsUserActivationIfUserAlreadyActivated() {
        worker.onUserCreated(new UserCreatedEvent(TestFactories.users().createActiveUser("test@test.com")));

        verify(stat).incrementUserCreation();
        verify(stat).incrementActivationCount();
    }

    @Test
    public void canIncrementActivation() {
        User user = TestFactories.users().createActiveUser("test@test.com");

        worker.onUserActivated(new UserActivatedEvent(user.getId()));

        verify(stat).incrementActivationCount();
    }

    @Test
    public void doNotIncrementActivationIfOldUser() {
        DateTimeUtils.setCurrentMillisFixed(new DateTime().minusDays(1).getMillis());
        User user = TestFactories.users().createActiveUser("test@test.com");
        DateTimeUtils.setCurrentMillisSystem();

        worker.onUserActivated(new UserActivatedEvent(user.getId()));

        verify(stat, never()).incrementActivationCount();
    }

    @Test
    public void incrementLogInCount() {
        User user = TestFactories.users().createActiveUser("test@test.com");

        worker.onSessionCreated(new SessionCreatedEvent(UUID.randomUUID(), user.getId()));

        verify(stat).incrementLoginCount();

    }

    private DailyUserStatisticsCounter stat;
    private DailyStatisticsWorker worker;
}
