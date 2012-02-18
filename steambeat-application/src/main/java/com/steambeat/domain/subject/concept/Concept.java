package com.steambeat.domain.subject.concept;

import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.thesaurus.*;

import java.util.UUID;

public class Concept extends Subject {

    public Concept(final String text) {
        super(UUID.randomUUID().toString());
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Language getLanguage() {
        return language;
    }

    void setLanguage(final Language language) {
        this.language = language;
    }

    public Category getCategory() {
        return category;
    }

    private final String text;
    private Language language;
    Category category;
}
