package com.steambeat.domain.subject.concept;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TestsConcept {

    @Test
    public void theIdIsTheText() {
        final Concept concept = new Concept("Maison Blanche");

        assertThat(concept.getId(), is("Maison Blanche"));
    }
}
