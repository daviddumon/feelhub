package com.feelhub.domain.statistics;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.feeling.*;
import org.joda.time.DateTime;

import java.util.UUID;

public class Statistics extends BaseEntity {

    protected Statistics() {
    }

    public Statistics(final UUID topicId, final Granularity granularity, final DateTime date) {
        this.id = UUID.randomUUID();
        this.date = date;
        this.topicId = topicId;
        this.granularity = granularity;
    }

    public void incrementFeelingCount(final Feeling feeling) {
        incrementSentimentCountForSentimentValue(feeling.getFeelingValue());
    }

    private void incrementSentimentCountForSentimentValue(final FeelingValue feelingValue) {
        switch (feelingValue) {
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

    public UUID getTopicId() {
        return topicId;
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

    public void setTopicId(final UUID topicId) {
        this.topicId = topicId;
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
    private UUID topicId;
    private Granularity granularity;
    private DateTime date;
    private int good;
    private int bad;
    private int neutral;
}
