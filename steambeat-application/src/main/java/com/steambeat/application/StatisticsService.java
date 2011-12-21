package com.steambeat.application;

import com.steambeat.domain.DomainEventListener;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.statistics.*;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.repositories.Repositories;

import java.util.List;

public class StatisticsService implements DomainEventListener<OpinionPostedEvent> {

    // todo: les statistiques s'occupent uniquement des jugements, pas des opinions qui ne sont qu'un agregat
    public StatisticsService() {
        //DomainEventBus.INSTANCE.register(this, OpinionPostedEvent.class);
    }

    @Override
    public void notify(final OpinionPostedEvent event) {
        opinionOn(event.getOpinion().getJudgments().get(0).getSubject(), event.getOpinion());
    }

    public void opinionOn(final Subject subject, final Opinion opinion) {
        for (final Granularity granularity : Granularity.values()) {
            dealWith(granularity, subject, opinion);
            dealWith(granularity, steambeat, opinion);
        }
    }

    private void dealWith(final Granularity granularity, final Subject subject, final Opinion opinion) {
        final Statistics stat = getOrCreateStat(granularity, subject, opinion);
        stat.incrementOpinionCount(opinion);
    }

    private synchronized Statistics getOrCreateStat(final Granularity granularity, final Subject subject, final Opinion opinion) {
        final List<Statistics> statistics = Repositories.statistics().forSubject(subject, granularity, granularity.intervalFor(opinion.getCreationDate()));
        final Statistics stat;
        if (statistics.size() == 0) {
            stat = new Statistics(subject, granularity, opinion.getCreationDate());
            Repositories.statistics().add(stat);
        } else {
            stat = statistics.get(0);
        }
        return stat;
    }

    private final WebPage steambeat = new WebPage(new Association(new Uri("steambeat"), null));
}
