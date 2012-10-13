package com.steambeat.domain.opinion;

public class Subject {

    public Subject(final Feeling feeling, final String text) {
        this.feeling = feeling;
        this.text = text;
    }

    Feeling feeling;
    String text;
}
