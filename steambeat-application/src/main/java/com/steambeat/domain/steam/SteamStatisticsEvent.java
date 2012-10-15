package com.steambeat.domain.steam;

import com.steambeat.domain.eventbus.DomainEvent;
import com.steambeat.domain.opinion.Judgment;

public class SteamStatisticsEvent extends DomainEvent {

    public SteamStatisticsEvent(final Judgment judgment) {
        this.judgment = judgment;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("SteamStatisticsEvent ");
        stringBuilder.append(judgment.getFeeling());
        stringBuilder.append(" on ");
        stringBuilder.append(judgment.getReferenceId());
        return stringBuilder.toString();

    }

    public Judgment getJudgment() {
        return judgment;
    }

    private Judgment judgment;
}
