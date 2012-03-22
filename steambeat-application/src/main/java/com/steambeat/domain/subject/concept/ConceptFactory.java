package com.steambeat.domain.subject.concept;

import com.steambeat.domain.textAnalytics.NamedEntity;
import com.steambeat.domain.thesaurus.*;

public class ConceptFactory {

    public Concept newConcept(final NamedEntity namedEntity) {
        final Concept concept = new Concept(namedEntity.text);
        concept.setLanguage(Language.forString(namedEntity.language));
        concept.category = Category.forString(namedEntity.type);
        return concept;
    }
}
