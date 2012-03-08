package com.steambeat.domain.opinion;

import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.Repositories;

public class Judgment {

    // Constructor for mapper : do not delete !
    protected Judgment() {
    }

    public Judgment(final Subject subject, final Feeling feeling) {
        this.subjectId = subject.getId();
        this.feeling = feeling;
    }

    public Feeling getFeeling() {
        return feeling;
    }

    public Subject getSubject() {
        return Repositories.subjects().get(subjectId);
    }

    public String getSubjectId() {
        return subjectId;
    }

    private Feeling feeling;
    private String subjectId;
}
