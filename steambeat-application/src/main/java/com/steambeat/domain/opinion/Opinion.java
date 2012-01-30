package com.steambeat.domain.opinion;

import com.google.common.collect.Lists;
import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.subject.Subject;
import org.joda.time.DateTime;

import java.util.*;

public class Opinion extends BaseEntity {

    protected Opinion() {
    }

    public Opinion(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public Date getCreationDateAsDate() {
        return creationDate.toDate();
    }

    //todo
    public String getSubjectId() {
        return judgments.get(0).getSubjectId();
    }

    //todo
    public Feeling getFeeling() {
        return judgments.get(0).getFeeling();
    }

    public String getId() {
        return id;
    }

    public List<Judgment> getJudgments() {
        return judgments;
    }

    public void addJudgment(final Subject subject, final Feeling feeling) {
        judgments.add(new Judgment(subject, feeling));
    }

    private String text;
    private final DateTime creationDate = new DateTime();
    private String id;
    private final List<Judgment> judgments = Lists.newArrayList();
}
