package com.steambeat.domain.statistics;

import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.topic.Topic;
import org.joda.time.DateTime;

import java.util.UUID;

public class Statistics extends BaseEntity {

    protected Statistics() {
    }

    public Statistics(final Topic topic, final Granularity granularity, final DateTime date) {
        this.id = UUID.randomUUID();
        this.date = date;
        topicId = topic.getId();
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

    public int getNeutral() {
        return neutral;
    }

    private UUID id;
    private UUID topicId;
    private Granularity granularity;
    private DateTime date;
    private int good;
    private int bad;
    private int neutral;
}
