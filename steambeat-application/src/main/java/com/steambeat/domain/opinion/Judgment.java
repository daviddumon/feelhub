package com.steambeat.domain.opinion;

public class Judgment {

    public Judgment(final String subjectId, final Feeling feeling) {
        this.subjectId = subjectId;
        this.feeling = feeling;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public Feeling getFeeling() {
        return feeling;
    }

    private String subjectId;
    private Feeling feeling;
}
