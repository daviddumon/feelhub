package com.steambeat.domain.statistics;

import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.reference.Reference;
import org.joda.time.DateTime;

import java.util.UUID;

public class Statistics extends BaseEntity {

    protected Statistics() {
    }

    public Statistics(final Reference reference, final Granularity granularity, final DateTime date) {
        this.id = UUID.randomUUID();
        this.date = date;
        referenceId = reference.getId();
        this.granularity = granularity;
    }

    public void incrementJudgmentCount(final Judgment judgment) {
        incrementJudgmentCountForFeeling(judgment.getFeeling());
    }

    private void incrementJudgmentCountForFeeling(final Feeling feeling) {
        switch (feeling) {
            case good:
                good++;
                break;
            case bad:
                bad++;
                break;
            case neutral:
                neutral++;
                break;
        }
    }

    @Override
    public UUID getId() {
        return id;
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    public Granularity getGranularity() {
        return granularity;
    }

    public DateTime getDate() {
        return date;
    }

    public int getGood() {
        return good;
    }

    public int getBad() {
        return bad;
    }

    public int getNeutral() {
        return neutral;
    }

    private UUID id;
    private UUID referenceId;
    private Granularity granularity;
    private DateTime date;
    private int good;
    private int bad;
    private int neutral;
}
