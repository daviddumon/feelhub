package com.steambeat.domain.subject.concept;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsConcept {

    @Test
    public void theIdIsTheText() {
        final Concept concept = new Concept("Maison Blanche");

        assertThat(concept.getId(), is("Maison Blanche"));
    }
}
