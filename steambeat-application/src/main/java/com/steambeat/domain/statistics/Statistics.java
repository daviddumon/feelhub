package com.steambeat.domain.statistics;

import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.subject.Subject;
import org.joda.time.DateTime;

public class Statistics extends BaseEntity {

    protected Statistics() {
    }

    public Statistics(final Subject subject, final Granularity granularity, final DateTime date) {
        this.date = date;
        subjectId = subject.getId();
        this.granularity = granularity;
    }

    public void incrementOpinionCount(final Opinion opinion) {
        incrementOpinionCountForFeeling(opinion.getJudgments().get(0).getFeeling());
    }

    public void incrementOpinionCountForFeeling(final Feeling feeling) {
        switch (feeling) {
            case good:
                goodOpinions++;
                break;
            case bad:
                badOpinions++;
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

    private String id;
    private String subjectId;
    private Granularity granularity;
    private DateTime date;
    private int goodOpinions;
    private int badOpinions;
}
