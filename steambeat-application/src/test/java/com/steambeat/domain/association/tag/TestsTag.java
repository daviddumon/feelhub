package com.steambeat.domain.association.tag;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsTag {

    @Test
    public void trimTag() {
        final Tag tag = new Tag(" need a trim");

        assertThat(tag.toString(), is("need a trim"));
    }

    @Test
    public void lowerCaseTag() {
        final Tag tag = new Tag("TaGs");

        assertThat(tag.toString(), is("tags"));
    }
}
