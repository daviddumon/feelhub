package com.feelhub.analytic.live;

import com.feelhub.analytic.StatisticsCounter;
import com.feelhub.analytic.StatisticsCounterExecutor;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.FeelingCreatedEvent;
import com.feelhub.domain.session.SessionCreatedEvent;
import com.feelhub.domain.topic.http.HttpTopicCreatedEvent;
import com.feelhub.domain.topic.real.RealTopicCreatedEvent;
import com.feelhub.domain.user.UserCreatedEvent;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.joda.time.DateMidnight;

import javax.inject.Inject;

@SuppressWarnings("UnusedParameters")
public class LiveDailyStatisticsWorker {

    @Inject
    public LiveDailyStatisticsWorker(StatisticsCounterExecutor executor) {
        this.executor = executor;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onUserCreated(UserCreatedEvent event) {
        StatisticsCounter counter = counterUpdate().inc("signups");
        executor.execute(counter);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onSessionCreated(SessionCreatedEvent sessionCreatedEvent) {
        executor.execute(counterUpdate().inc("logins"));
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onHttpTopicCreated(HttpTopicCreatedEvent event) {
        executor.execute(counterUpdate().inc("httpTopics").inc("topics"));
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onRealTopicCreated(RealTopicCreatedEvent event) {
        executor.execute(counterUpdate().inc("realTopics").inc("topics"));
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onFeelingCreated(FeelingCreatedEvent event) {
        executor.execute(counterUpdate().inc("feelings"));
    }

    private StatisticsCounter counterUpdate() {
        return new StatisticsCounter("dailylivestatistics").withId("date", new DateMidnight().toDate());
    }

    private StatisticsCounterExecutor executor;
}