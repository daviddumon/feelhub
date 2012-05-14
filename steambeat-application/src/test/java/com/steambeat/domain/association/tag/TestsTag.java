package com.steambeat.domain.association.tag;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsTag {

    @Test
    public void lowercaseText() {
        final Tag tag = new Tag("TexT");

        assertThat(tag.toString(), is("text"));
    }

    @Test
    public void canTrim() {
        final Tag tag = new Tag(" text ");

        assertThat(tag.toString(), is("text"));
    }
}
