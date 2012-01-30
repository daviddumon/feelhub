package com.steambeat.domain.subject.concept;

import com.steambeat.domain.textAnalytics.NamedEntity;
import com.steambeat.domain.thesaurus.*;
import org.junit.Test;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

public class TestsConceptFactory {

    @Test
    public void canCreateNamedEntity() {
        final ConceptFactory conceptFactory = new ConceptFactory();
        final NamedEntity namedEntity = new NamedEntity();
        namedEntity.text = "Agile";
        namedEntity.language = "english";
        namedEntity.type = "Method";

        final Concept concept = conceptFactory.newConcept(namedEntity);

        assertThat(concept.getText(), is("Agile"));
        assertThat(concept.getLanguage(), is(Language.forString("english")));
        assertThat(concept.getCategory(), is(Category.forString("Method")));
    }
}
