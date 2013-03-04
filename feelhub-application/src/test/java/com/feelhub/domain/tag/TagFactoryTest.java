package com.feelhub.domain.tag;

import com.google.inject.*;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TagFactoryTest {

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        tagFactory = injector.getInstance(TagFactory.class);
    }

    @Test
    public void canCreateTag() {
        final String tagValue = "value";

        final Tag tag = tagFactory.createTag(tagValue);

        assertThat(tag).isNotNull();
        assertThat(tag.getId()).isEqualTo(tagValue);
    }

    private TagFactory tagFactory;
}
