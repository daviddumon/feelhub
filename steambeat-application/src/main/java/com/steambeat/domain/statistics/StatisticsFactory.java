package com.steambeat.domain.statistics;

import com.steambeat.domain.*;
import com.steambeat.domain.opinion.*;
import com.steambeat.repositories.Repositories;

import java.util.List;

public class StatisticsFactory implements DomainEventListener<JudgmentPostedEvent> {

    public StatisticsFactory() {
        DomainEventBus.INSTANCE.register(this);
    }

    @Override
    public void handle(final JudgmentPostedEvent event) {
        judgmentOn(event);
    }

    private void judgmentOn(final JudgmentPostedEvent event) {
        for (final Granularity granularity : Granularity.values()) {
            dealWith(granularity, event);
        }
    }

    private void dealWith(final Granularity granularity, final JudgmentPostedEvent event) {
        dealWithSubject(granularity, event);
        dealWithSteam(granularity, new JudgmentPostedEvent(new Judgment(Repositories.subjects().getSteam(), event.getJudgment().getFeeling())));
    }

    private void dealWithSubject(final Granularity granularity, final JudgmentPostedEvent event) {
        final Statistics stat = getOrCreateStat(granularity, event);
        stat.incrementJudgmentCount(event.getJudgment());
    }

    private void dealWithSteam(final Granularity granularity, final JudgmentPostedEvent event) {
        final Statistics stat = getOrCreateStat(granularity, event);
        stat.incrementJudgmentCount(event.getJudgment());
    }

    private synchronized Statistics getOrCreateStat(final Granularity granularity, final JudgmentPostedEvent event) {
        final List<Statistics> statistics = Repositories.statistics().forSubject(event.getJudgment().getSubject(), granularity, granularity.intervalFor(event.getDate()));
        final Statistics stat;
        if (statistics.isEmpty()) {
            stat = new Statistics(event.getJudgment().getSubject(), granularity, event.getDate());
            Repositories.statistics().add(stat);
        } else {
            stat = statistics.get(0);
        }
        return stat;
    }
}
