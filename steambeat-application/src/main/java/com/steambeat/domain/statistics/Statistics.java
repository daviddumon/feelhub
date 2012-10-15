package com.steambeat.domain.statistics;

import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.opinion.*;
import org.joda.time.DateTime;

import java.util.UUID;

public class Statistics extends BaseEntity {

    protected Statistics() {
    }

    public Statistics(final UUID referenceId, final Granularity granularity, final DateTime date) {
        this.id = UUID.randomUUID();
        this.date = date;
        this.referenceId = referenceId;
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

    public void setReferenceId(final UUID referenceId) {
        this.referenceId = referenceId;
    }

    public int getNeutral() {
        return neutral;
    }

    public void addGood(final int good) {
        this.good += good;
    }

    public void addBad(final int bad) {
        this.bad += bad;
    }

    public void addNeutral(final int neutral) {
        this.neutral += neutral;
    }

    private UUID id;
    private UUID referenceId;
    private Granularity granularity;
    private DateTime date;
    private int good;
    private int bad;
    private int neutral;
}
