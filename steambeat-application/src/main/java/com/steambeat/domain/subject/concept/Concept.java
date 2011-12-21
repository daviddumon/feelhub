package com.steambeat.domain.subject.concept;

import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.thesaurus.Category;
import com.steambeat.domain.thesaurus.Language;

public class Concept extends Subject {

    public Concept(String text) {
        super(text);
    }

    public String getText() {
        return getId();
    }

    public Language getLanguage() {
        return language;
    }

    void setLanguage(Language language) {
        this.language = language;
    }

    public Category getCategory() {
        return category;
    }

    private Language language;
    Category category;
}
