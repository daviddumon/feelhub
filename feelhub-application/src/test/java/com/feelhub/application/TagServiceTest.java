package com.feelhub.application;

import com.feelhub.domain.tag.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.Assertions.*;

public class TagServiceTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        tagService = injector.getInstance(TagService.class);
    }

    @Test
    public void canFindTagForName() {
        final Tag tag = TestFactories.tags().newTag();

        final Tag foundTag = tagService.lookUp(tag.getId());

        assertThat(foundTag).isNotNull();
        assertThat(foundTag).isEqualTo(tag);
    }

    @Test
    public void throwExceptionIfNoTagFound() {
        exception.expect(TagNotFoundException.class);

        TestFactories.tags().newTag();

        tagService.lookUp("anothervalue");
    }

    @Test
    public void canCreateTag() {
        final String value = "value";

        final Tag tag = tagService.createTag(value);

        assertThat(Repositories.tags().getAll().size()).isEqualTo(1);
        assertThat(Repositories.tags().getAll().get(0)).isEqualTo(tag);
    }

    @Test
    public void canCreateTagInLowercase() {
        final String value = "TaG";

        final Tag tag = tagService.createTag(value);

        assertThat(tag.getId()).isEqualTo(value.toLowerCase());
    }

    @Test
    public void lookUpUsesLowercase() {
        final String description = "description";
        Repositories.tags().add(new Tag(description));

        final Tag tag = tagService.lookUp(description.toUpperCase());

        assertThat(tag).isNotNull();
    }

    private TagService tagService;
}
