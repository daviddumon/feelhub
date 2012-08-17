package com.steambeat.domain.subject.concept;

import com.steambeat.domain.keyword.Keyword;

public class ConceptTestFactory {

    public Concept newConcept(final Keyword keyword) {
        final Concept concept = new Concept();
        concept.addIfAbsent(keyword);
        return concept;
    }

    public Concept newConcept() {
        final Concept concept = new Concept();
        return concept;
    }
}
