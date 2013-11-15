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
        incrementFeelingForFeelingValue(feeling.getFeelingValue());
    }

    private void incrementFeelingForFeelingValue(final FeelingValue feelingValue) {
        switch (feelingValue) {
            case happy:
                happy++;
                break;
            case sad:
                sad++;
                break;
            case bored:
                bored++;
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

    public int getHappy() {
        return happy;
    }

    public int getSad() {
        return sad;
    }

    public void setTopicId(final UUID topicId) {
        this.topicId = topicId;
    }

    public int getBored() {
        return bored;
    }

    public void addHappy(final int happy) {
        this.happy += happy;
    }

    public void addSad(final int sad) {
        this.sad += sad;
    }

    public void addBored(final int bored) {
        this.bored += bored;
    }

    private UUID id;
    private UUID topicId;
    private Granularity granularity;
    private DateTime date;
    private int happy;
    private int sad;
    private int bored;
}
