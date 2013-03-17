package com.feelhub.analytic.daily;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.session.SessionCreatedEvent;
import com.feelhub.domain.user.UserCreatedEvent;
import com.feelhub.domain.user.activation.UserActivatedEvent;
import com.feelhub.repositories.Repositories;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.joda.time.DateMidnight;

import javax.inject.Inject;
import java.util.UUID;

public class DailyStatisticsWorker {

    @Inject
    public DailyStatisticsWorker(DailyUserStatisticsCounter statistics) {
        this.statistics = statistics;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onUserCreated(UserCreatedEvent event) {
        statistics.incrementUserCreation();
        if(fromFacebook(event)) {
            statistics.incrementActivationCount();
        }
    }

    private boolean fromFacebook(UserCreatedEvent event) {
        return event.user.isActive();
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onUserActivated(UserActivatedEvent event) {
        if (notCreatedToday(event.userId)) {
            return;
        }
        statistics.incrementActivationCount();
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onSessionCreated(SessionCreatedEvent sessionCreatedEvent) {
        if(notCreatedToday(sessionCreatedEvent.userId)) {
            return;
        }
        statistics.incrementLoginCount();
    }

    private boolean notCreatedToday(UUID userId) {
        return !new DateMidnight(Repositories.users().get(userId).getCreationDate()).isEqual(new DateMidnight());
    }

    private DailyUserStatisticsCounter statistics;
}
