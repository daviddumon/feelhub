package com.feelhub.domain.tag;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.topic.*;
import com.feelhub.repositories.*;
import com.feelhub.repositories.fakeRepositories.*;
import com.google.inject.*;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsTagIndexer {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(SessionProvider.class).to(FakeSessionProvider.class);
            }
        });
        tagIndexer = injector.getInstance(TagIndexer.class);
    }

    @Test
    public void canIndexTagsFromEvent() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID(), FakeUniqueTopicType.NotUnique);
        final String tag = "tag-fr";
        final TagRequestEvent tagRequestEvent = new TagRequestEvent(fakeTopic, tag);

        DomainEventBus.INSTANCE.post(tagRequestEvent);

        assertThat(Repositories.tags().getAll().size()).isEqualTo(1);
    }

    @Test
    public void correctlySetTopicIdInTag() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID(), FakeUniqueTopicType.NotUnique);
        final String tag = "tag-fr";
        final TagRequestEvent tagRequestEvent = new TagRequestEvent(fakeTopic, tag);

        DomainEventBus.INSTANCE.post(tagRequestEvent);

        assertThat(Repositories.tags().getAll().get(0).getTopicIds()).contains(fakeTopic.getId());
    }

    @Test
    public void correctlySetTopicIdInTagWhenTagUniqueness() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID(), FakeUniqueTopicType.Unique);
        final String tag = "tag-fr";
        final TagRequestEvent tagRequestEvent = new TagRequestEvent(fakeTopic, tag);

        DomainEventBus.INSTANCE.post(tagRequestEvent);

        assertThat(Repositories.tags().getAll().get(0).getTopicIds()).contains(fakeTopic.getId());
    }

    @Test
    public void canUseExistingTag() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID(), FakeUniqueTopicType.NotUnique);
        final String tag = "tag-fr";
        final TagRequestEvent tagRequestEvent = new TagRequestEvent(fakeTopic, tag);
        Repositories.tags().add(new Tag(tag.toLowerCase()));

        DomainEventBus.INSTANCE.post(tagRequestEvent);

        assertThat(Repositories.tags().getAll().size()).isEqualTo(1);
    }

    @Test
    public void changeCurrentIdIfExistingUniqueTopicType() {
        final String value = "tag";
        final FakeTopic fakeTopic = createTagForFakeUniqueTopic(value);
        final FakeTopic anotherTopic = new FakeTopic(UUID.randomUUID(), FakeUniqueTopicType.Unique);
        Repositories.topics().add(anotherTopic);
        final TagRequestEvent tagRequestEvent = new TagRequestEvent(anotherTopic, value);

        DomainEventBus.INSTANCE.post(tagRequestEvent);

        final Tag tag = Repositories.tags().getAll().get(0);
        assertThat(tag.getTopicIds().size()).isEqualTo(1);
        assertThat(tag.getTopicIds()).contains(fakeTopic.getId());
        assertThat(anotherTopic.getCurrentId()).isEqualTo(fakeTopic.getId());
    }

    private FakeTopic createTagForFakeUniqueTopic(final String value) {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID(), FakeUniqueTopicType.Unique);
        final Tag tag = new Tag(value);
        tag.addTopic(fakeTopic);
        Repositories.tags().add(tag);
        Repositories.topics().add(fakeTopic);
        return fakeTopic;
    }

    enum FakeUniqueTopicType implements TopicType {
        Unique(true),
        NotUnique(false);

        FakeUniqueTopicType(final boolean unique) {
            isUnique = unique;
        }

        @Override
        public boolean hasTagUniqueness() {
            return isUnique;
        }

        private final boolean isUnique;
    }

    class FakeTopic extends Topic {
        FakeTopic(final UUID id, final TopicType type) {
            super(id);
            this.type = type;
        }

        @Override
        public TopicType getType() {
            return type;
        }

        private final TopicType type;
    }

    private TagIndexer tagIndexer;
}
