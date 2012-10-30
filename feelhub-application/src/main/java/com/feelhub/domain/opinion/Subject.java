package com.feelhub.domain.opinion;

public class Subject {

    public Subject(final Feeling feeling, final String text) {
        this.feeling = feeling;
        this.text = text;
    }

    public Feeling feeling;
    public String text;
}
