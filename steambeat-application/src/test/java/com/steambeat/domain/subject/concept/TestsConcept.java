package com.steambeat.domain.subject.concept;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsConcept {

    @Test
    public void theIdIsTheText() {
        final String text = "Maison Blanche";
        final Concept concept = new Concept(text);

        assertThat(concept.getId(), notNullValue());
        assertThat(concept.getId().toString(), not(text));
        assertThat(concept.getText(), is(text));
    }
}
