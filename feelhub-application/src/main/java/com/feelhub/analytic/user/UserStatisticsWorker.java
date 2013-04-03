package com.feelhub.analytic.user;

import com.feelhub.analytic.*;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.FeelingCreatedEvent;
import com.feelhub.domain.session.SessionCreatedEvent;
import com.feelhub.domain.topic.http.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.user.UserCreatedEvent;
import com.feelhub.repositories.Repositories;
import com.google.common.eventbus.*;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.util.UUID;

public class UserStatisticsWorker {

    @Inject
    public UserStatisticsWorker(final StatisticsCounterExecutor executor) {
        this.executor = executor;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onUserCreated(final UserCreatedEvent userCreatedEvent) {
        final StatisticsCounter counter = counterUpdate(userCreatedEvent.user.getId())
                .set("creationDate", userCreatedEvent.user.getCreationDate().toDate());
        executor.execute(counter);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onSessionCreated(final SessionCreatedEvent event) {
        executor.execute(counterUpdate(event.userId).inc(getTimedInc(LOGINS)).inc(LOGINS));
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onFeelingCreated(final FeelingCreatedEvent event) {
        executor.execute(counterUpdate(event.userId).inc(getTimedInc(FEELINGS)).inc(FEELINGS));
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onHttpTopicCreated(final HttpTopicCreatedEvent event) {
        final HttpTopic httpTopic = Repositories.topics().getHttpTopic(event.topicId);
        if (httpTopic.hasUser()) {
            executor.execute(counterUpdate(httpTopic.getUserId()).inc(getTimedInc(HTTP_TOPICS)).inc(HTTP_TOPICS));
        }
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onRealTopicCreated(final RealTopicCreatedEvent event) {
        final RealTopic topic = Repositories.topics().getRealTopic(event.topicId);
        if (topic.hasUser()) {
            executor.execute(counterUpdate(topic.getUserId()).inc(getTimedInc(REAL_TOPICS)).inc(REAL_TOPICS));
        }
    }

    private StatisticsCounter counterUpdate(final UUID userId) {
        return new StatisticsCounter("userstatistic").withId("_id", userId);
    }

    private String getTimedInc(final String key) {
        return new DateTime().toString("y.M.d") + "." + key;
    }


    public static final String REAL_TOPICS = "realTopics";
    public static final String HTTP_TOPICS = "httpTopics";
    public static final String FEELINGS = "feelings";
    public static final String LOGINS = "logins";

    private final StatisticsCounterExecutor executor;
}
