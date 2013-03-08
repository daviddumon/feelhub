package com.feelhub.application;

import com.feelhub.domain.tag.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
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
        tagService = new TagService();
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
    public void lookUpUsesLowercase() {
        final String description = "description";
        Repositories.tags().add(new Tag(description));

        final Tag tag = tagService.lookUp(description.toUpperCase());

        assertThat(tag).isNotNull();
    }

    private TagService tagService;
}
