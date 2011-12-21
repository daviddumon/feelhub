package com.steambeat.domain.subject.concept;

import com.steambeat.domain.textAnalytics.NamedEntity;
import com.steambeat.domain.thesaurus.Category;
import com.steambeat.domain.thesaurus.Language;

public class ConceptFactory {
    public Concept newConcept(NamedEntity namedEntity) {
        final Concept result = new Concept(namedEntity.text);
        result.setLanguage(Language.forString(namedEntity.language));
        result.category = Category.forString(namedEntity.type);
        return result;
    }
}
