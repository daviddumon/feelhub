package com.steambeat.domain.subject.concept;

import com.steambeat.domain.scrapers.Scraper;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.thesaurus.*;

import java.util.UUID;

public class Concept extends Subject {

    // mongolink constructor : do not delete
    public Concept() {
    }

    public Concept(final String text) {
        super(UUID.randomUUID());
        this.text = text;
    }

    @Override
    public void setScraper(final Scraper scraper) {
        update(scraper);
    }

    @Override
    protected void update(final Scraper scraper) {
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

    private String text;
    private Language language;
    Category category;
}
