package com.steambeat.domain.subject.concept;

import com.steambeat.domain.textAnalytics.NamedEntity;
import com.steambeat.domain.thesaurus.*;

public class ConceptFactory {
    public Concept newConcept(final NamedEntity namedEntity) {
        final Concept result = new Concept(namedEntity.text);
        result.setLanguage(Language.forString(namedEntity.language));
        result.category = Category.forString(namedEntity.type);
        return result;
    }
}
