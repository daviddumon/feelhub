package com.steambeat.domain.opinion;

import com.google.common.collect.Lists;
import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.Repositories;
import org.joda.time.DateTime;

import java.util.*;

public class Opinion extends BaseEntity {

    protected Opinion() {
    }

    public Opinion(final String text, final Feeling feeling, final Subject subject) {
        this.text = text;
        this.feeling = feeling;
        this.subjectId = subject.getId();
        creationDate = new DateTime();
        this.judgments = Lists.newArrayList();
    }

    public Opinion(final String text, final List<Judgment> judgments) {
        this.text = text;
        creationDate = new DateTime();
        this.judgments = judgments;
    }

    public String getText() {
        return text;
    }

    public Feeling getFeeling() {
        return feeling;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public Date getCreationDateAsDate() {
        return creationDate.toDate();
    }

    public void setText(final String text) {
        this.text = text;
    }

    public Subject getSubject() {
        return Repositories.webPages().get(subjectId);
    }

    public Object getSubjectId() {
        return subjectId;
    }

    @Override
    public String getId() {
        return id;
    }

    public List<Judgment> getJudgments() {
        return judgments;
    }

    private String text;
    private Feeling feeling;
    private Object subjectId;
    private DateTime creationDate;
    private String id;
    private List<Judgment> judgments;
}
