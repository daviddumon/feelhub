package com.steambeat.domain.opinion;

import com.steambeat.domain.BaseEntity;
import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.Repositories;
import org.joda.time.DateTime;

import java.util.Date;

public class Opinion extends BaseEntity {

    protected Opinion() {
    }

    public Opinion(final String text, final Feeling feeling, final Subject subject) {
        this.text = text;
        this.feeling = feeling;
        this.subjectId = subject.getId();
        creationDate = new DateTime();
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

    public void setText(String text) {
        this.text = text;
    }

    public Subject getSubject() {
        return Repositories.feeds().get(subjectId);
    }

    public Object getSubjectId() {
        return subjectId;
    }

    @Override
    public String getId() {
        return id;
    }

    private String text;
    private Feeling feeling;
    private Object subjectId;
    private DateTime creationDate;
    private String id;
}
