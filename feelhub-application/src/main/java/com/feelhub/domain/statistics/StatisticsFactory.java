package com.feelhub.domain.statistics;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.opinion.*;
import com.feelhub.domain.world.WorldStatisticsEvent;
import com.feelhub.repositories.*;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import org.joda.time.DateTime;

import java.util.List;

public class StatisticsFactory {

    @Inject
    public StatisticsFactory(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final JudgmentStatisticsEvent event) {
        sessionProvider.start();
        judgmentOn(event.getJudgment(), event.getDate());
        sessionProvider.stop();
    }

    @Subscribe
    public void handle(final WorldStatisticsEvent event) {
        sessionProvider.start();
        judgmentOn(event.getJudgment(), event.getDate());
        sessionProvider.stop();
    }

    private void judgmentOn(final Judgment judgment, final DateTime date) {
        for (final Granularity granularity : Granularity.values()) {
            dealWith(granularity, judgment, date);
        }
    }

    private void dealWith(final Granularity granularity, final Judgment judgment, final DateTime date) {
        dealWithReference(granularity, judgment, date);
    }

    private void dealWithReference(final Granularity granularity, final Judgment judgment, final DateTime date) {
        final Statistics stat = getOrCreateStat(granularity, judgment, date);
        stat.incrementJudgmentCount(judgment);
    }

    private synchronized Statistics getOrCreateStat(final Granularity granularity, final Judgment judgment, final DateTime date) {
        final List<Statistics> statistics = Repositories.statistics().forReferenceId(judgment.getReferenceId(), granularity, granularity.intervalFor(date));
        final Statistics stat;
        if (statistics.isEmpty()) {
            stat = new Statistics(judgment.getReferenceId(), granularity, date);
            Repositories.statistics().add(stat);
        } else {
            stat = statistics.get(0);
        }
        return stat;
    }

    private final SessionProvider sessionProvider;
}
