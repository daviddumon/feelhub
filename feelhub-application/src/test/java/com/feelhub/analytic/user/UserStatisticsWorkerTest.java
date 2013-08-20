package com.feelhub.analytic.user;

import com.feelhub.analytic.*;
import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.feeling.FeelingCreatedEvent;
import com.feelhub.domain.session.SessionCreatedEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.user.*;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.joda.time.*;
import org.junit.*;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class UserStatisticsWorkerTest {

    @Before
    public void setUp() throws Exception {
        counterExecutor = mock(StatisticsCounterExecutor.class);
        worker = new UserStatisticsWorker(counterExecutor);
        DateTimeUtils.setCurrentMillisFixed(new DateTime(2013, 1, 1, 0, 0).getMillis());
    }

    @Test
    public void createStatOnCreation() {
        final User user = TestFactories.users().createActiveUser("test@test.com");

        worker.onUserCreated(new UserCreatedEvent(user));

        final StatisticsCounter statisticsCounter = lastCounter();
        assertThat(statisticsCounter.getCollectionName()).isEqualTo("userstatistic");
        assertThat(statisticsCounter.getIdField()).isEqualTo("_id");
        assertThat(statisticsCounter.getIdValue()).isEqualTo(user.getId());
        assertThat(statisticsCounter.hasSet("creationDate", user.getCreationDate().toDate())).isTrue();
    }

    private StatisticsCounter lastCounter() {
        final ArgumentCaptor<StatisticsCounter> captor = ArgumentCaptor.forClass(StatisticsCounter.class);
        verify(counterExecutor).execute(captor.capture());
        return captor.getValue();
    }

    @Test
    public void canIncrementsLoginCount() {
        final UUID userId = UUID.randomUUID();

        worker.onSessionCreated(new SessionCreatedEvent(UUID.randomUUID(), userId));

        final StatisticsCounter counter = lastCounter();
        assertThat(counter.hasInc("2013.1.1.logins")).isTrue();
    }

    @Test
    public void canIncrementFeeling() {
        worker.onFeelingCreated(new FeelingCreatedEvent(TestFactories.feelings().newFeeling()));

        final StatisticsCounter counter = lastCounter();
        assertThat(counter.hasInc("feelings")).isTrue();
        assertThat(counter.hasInc("2013.1.1.feelings")).isTrue();
    }

    @Test
    public void canIncrementHttpTopicCount() {
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();
        httpTopic.setUserId(UUID.randomUUID());

        worker.onHttpTopicCreated(new HttpTopicCreatedEvent(httpTopic.getId(), null));

        final StatisticsCounter counter = lastCounter();
        assertThat(counter.getIdValue()).isEqualTo(httpTopic.getUserId());
        assertThat(counter.hasInc("httpTopics")).isTrue();
        assertThat(counter.hasInc("2013.1.1.httpTopics")).isTrue();
    }

    @Test
    public void canIncrementRealTopicCount() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        realTopic.setUserId(UUID.randomUUID());

        worker.onRealTopicCreated(new RealTopicCreatedEvent(realTopic.getId(), FeelhubLanguage.REFERENCE));

        final StatisticsCounter counter = lastCounter();
        assertThat(counter.getIdValue()).isEqualTo(realTopic.getUserId());
        assertThat(counter.hasInc("realTopics")).isTrue();
        assertThat(counter.hasInc("2013.1.1.realTopics")).isTrue();
    }

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();
    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();
    private StatisticsCounterExecutor counterExecutor;
    private UserStatisticsWorker worker;
}
