package com.steambeat.domain.subject.concept;

import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.thesaurus.*;

public class Concept extends Subject {

    public Concept(final String text) {
        super(text);
    }

    public String getText() {
        return getId();
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

    private Language language;
    Category category;
}
