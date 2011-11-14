package com.steambeat.application;

import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.Repositories;
import com.steambeat.domain.*;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.statistics.*;
import com.steambeat.domain.subject.feed.*;

import java.util.List;


public class StatisticsService implements DomainEventListener<OpinionPostedEvent> {

    public StatisticsService() {
        DomainEventBus.INSTANCE.register(this, OpinionPostedEvent.class);
    }

    @Override
    public void notify(OpinionPostedEvent event) {
        opinionOn(event.getSubject(), event.getOpinion());
    }

    public void opinionOn(Subject subject, Opinion opinion) {
        for (Granularity granularity : Granularity.values()) {
            dealWith(granularity, subject, opinion);
            dealWith(granularity, steambeat, opinion);
        }
    }

    private void dealWith(Granularity granularity, Subject subject, Opinion opinion) {
        final Statistics stat = getOrCreateStat(granularity, subject, opinion);
        stat.incrementOpinionCount(opinion);
    }

    private synchronized Statistics getOrCreateStat(Granularity granularity, Subject subject, Opinion opinion) {
        final List<Statistics> statistics = Repositories.statistics().forFeed(subject, granularity, granularity.intervalFor(opinion.getCreationDate()));
        final Statistics stat;
        if (statistics.size() == 0) {
            stat = new Statistics(subject, granularity, opinion.getCreationDate());
            Repositories.statistics().add(stat);
        } else {
            stat = statistics.get(0);
        }
        return stat;
    }

    private Feed steambeat = new Feed(new Association(new Uri("steambeat"), null));
}
