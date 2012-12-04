package com.feelhub.application;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.tag.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsTagService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

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
    public void canFindTagForDescription() {
        final Tag tag = TestFactories.tags().newTag();

        final Tag foundTag = tagService.lookUp(tag.getId());

        assertThat(foundTag).isNotNull();
        assertThat(foundTag).isEqualTo(tag);
    }

    @Test
    public void throwExceptionIfNoTagFound() {
        exception.expect(TagNotFoundException.class);

        TestFactories.tags().newTag();

        tagService.lookUp("anotherdescription");
    }

    @Test
    public void canCreateTagsForTopic() {
        final Topic topic = TestFactories.topics().newTopic();

        tagService.createTags(topic);

        assertThat(Repositories.tags().getAll().size()).isEqualTo(2);
        assertThat(Repositories.tags().getAll().get(0).getTopicIds()).contains(topic.getId());
        assertThat(Repositories.tags().getAll().get(1).getTopicIds()).contains(topic.getId());
    }

    @Test
    public void createTagsInLowercase() {
        final Topic topic = new Topic(UUID.randomUUID());
        final String description = "Description";
        topic.addDescription(FeelhubLanguage.REFERENCE, description);

        tagService.createTags(topic);

        assertThat(Repositories.tags().getAll().get(0).getId()).isEqualTo(description.toLowerCase());
    }

    @Test
    public void lookUpUsesLowercase() {
        final String description = "description";
        Repositories.tags().add(new Tag(description));

        final Tag tag = tagService.lookUp(description.toUpperCase());

        assertThat(tag).isNotNull();
    }

    @Test
    public void createOnlyTagIfNotExists() {
        final String description = "description";
        Repositories.tags().add(new Tag(description));
        final Topic topic = new Topic(UUID.randomUUID());
        topic.addDescription(FeelhubLanguage.REFERENCE, "description");
        topic.addDescription(FeelhubLanguage.fromCode("fr"), "description-fr");

        tagService.createTags(topic);

        assertThat(Repositories.tags().getAll().size()).isEqualTo(2);
    }

    @Test
    public void correctlyAppendUUIDInExistingTag() {
        final String description = "description";
        Repositories.tags().add(new Tag(description));
        final Topic topic = new Topic(UUID.randomUUID());
        topic.addDescription(FeelhubLanguage.REFERENCE, "description");

        tagService.createTags(topic);

        assertThat(Repositories.tags().getAll().get(0).getTopicIds()).contains(topic.getId());
    }

    @Test
    public void subscribeToTagRequestEvent() {
        final Topic topic = TestFactories.topics().newTopic();
        final TagRequestEvent tagRequestEvent = new TagRequestEvent(topic);

        DomainEventBus.INSTANCE.post(tagRequestEvent);

        assertThat(Repositories.tags().getAll().size()).isEqualTo(2);
    }

    private TagService tagService;
}
