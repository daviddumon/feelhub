package com.steambeat.domain.statistics;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.opinion.JudgmentCreatedEvent;
import com.steambeat.repositories.*;

import java.util.List;

public class StatisticsFactory {

    @Inject
    public StatisticsFactory(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final JudgmentCreatedEvent event) {
        sessionProvider.start();
        judgmentOn(event);
        sessionProvider.stop();
    }

    private void judgmentOn(final JudgmentCreatedEvent event) {
        for (final Granularity granularity : Granularity.values()) {
            dealWith(granularity, event);
        }
    }

    private void dealWith(final Granularity granularity, final JudgmentCreatedEvent event) {
        dealWithReference(granularity, event);
        //dealWithSteam(granularity, new JudgmentPostedEvent(new Judgment(Repositories.subjects().getSteam(), event.getJudgment().getFeeling())));
    }

    private void dealWithReference(final Granularity granularity, final JudgmentCreatedEvent event) {
        final Statistics stat = getOrCreateStat(granularity, event);
        stat.incrementJudgmentCount(event.getJudgment());
    }

    private void dealWithSteam(final Granularity granularity, final JudgmentCreatedEvent event) {
        final Statistics stat = getOrCreateStat(granularity, event);
        stat.incrementJudgmentCount(event.getJudgment());
    }

    private synchronized Statistics getOrCreateStat(final Granularity granularity, final JudgmentCreatedEvent event) {
        final List<Statistics> statistics = Repositories.statistics().forReference(event.getJudgment().getReference(), granularity, granularity.intervalFor(event.getDate()));
        final Statistics stat;
        if (statistics.isEmpty()) {
            stat = new Statistics(event.getJudgment().getReference(), granularity, event.getDate());
            Repositories.statistics().add(stat);
        } else {
            stat = statistics.get(0);
        }
        return stat;
    }

    private final SessionProvider sessionProvider;
}
