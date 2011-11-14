package com.steambeat.domain.statistics;

import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.opinion.*;
import org.joda.time.DateTime;

public class Statistics extends BaseEntity {

    protected Statistics() {
    }

    public Statistics(Subject subject, Granularity granularity, DateTime date) {
        this.date = date;
        subjectId = subject.getId().toString();
        this.granularity = granularity;
    }

    public void incrementOpinionCount(final Opinion opinion) {
        incrementOpinionCount(opinion.getFeeling());
    }

    public void incrementOpinionCount(Feeling feeling) {
        switch (feeling) {
            case good:
                goodOpinions++;
                break;
            case bad:
                badOpinions++;
                break;
            case neutral:
                neutralOpinions++;
                break;
        }
    }

    @Override
    public String getId() {
        return id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public Granularity getGranularity() {
        return granularity;
    }

    public DateTime getDate() {
        return date;
    }

    public int getGoodOpinions() {
        return goodOpinions;
    }

    public int getBadOpinions() {
        return badOpinions;
    }

    public int getNeutralOpinions() {
        return neutralOpinions;
    }

    public void setGoodOpinions(int value) {
        this.goodOpinions = value;
    }

    public void setBadOpinions(int value) {
        this.badOpinions = value;
    }

    public void setNeutralOpinions(int value) {
        this.neutralOpinions = value;
    }

    private String id;
    private String subjectId;
    private Granularity granularity;
    private DateTime date;
    private int goodOpinions;
    private int badOpinions;
    private int neutralOpinions;
}
