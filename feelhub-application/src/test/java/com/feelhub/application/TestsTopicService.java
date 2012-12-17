package com.feelhub.application;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.restlet.data.MediaType;

import java.util.*;

import static org.fest.assertions.Assertions.*;

public class TestsTopicService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        final FakeUriResolver fakeUriResolver = new FakeUriResolver();
        canonicalUri = "http://www.urlcanonique.com";
        fakeUriResolver.thatFind(canonicalUri);
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(UriResolver.class).toInstance(fakeUriResolver);
            }
        });
        topicService = injector.getInstance(TopicService.class);
    }

    @Test
    public void canCreateARealTopic() {
        final User fakeActiveUser = TestFactories.users().createFakeActiveUser("mail@mail.com");

        final RealTopic realTopic = topicService.createRealTopic(FeelhubLanguage.REFERENCE, "name", RealTopicType.Automobile, fakeActiveUser);

        assertThat(realTopic).isNotNull();
        assertThat(realTopic.getUserId()).isEqualTo(fakeActiveUser.getId());
        assertThat(realTopic.getName(FeelhubLanguage.REFERENCE)).isEqualTo("Name");
        assertThat(Repositories.topics().getAll().size()).isEqualTo(1);
    }

    @Test
    public void canCreateAHttpTopic() {
        final HttpTopic httpTopic = topicService.createHttpTopic("value");

        assertThat(httpTopic).isNotNull();
        assertThat(Repositories.topics().getAll().size()).isEqualTo(1);
    }

    @Test
    public void canLookupTopic() {
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();
        Repositories.topics().add(httpTopic);

        final Topic topic = topicService.lookUp(httpTopic.getId());

        assertThat(topic).isEqualTo(httpTopic);
    }

    @Test
    public void canLookupCurrentTopic() {
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();
        final HttpTopic currentTopic = TestFactories.topics().newCompleteHttpTopic();
        httpTopic.changeCurrentId(currentTopic.getId());
        Repositories.topics().add(httpTopic);
        Repositories.topics().add(currentTopic);

        final Topic topic = topicService.lookUpCurrent(httpTopic.getId());

        assertThat(topic).isEqualTo(currentTopic);
    }

    @Test
    public void canGetTopicsFromString() {
        final String value = "tag";
        final Tag tag = TestFactories.tags().newTagWithoutTopic(value);
        tag.addTopic(TestFactories.topics().newCompleteRealTopic());
        tag.addTopic(TestFactories.topics().newCompleteRealTopic());
        tag.addTopic(TestFactories.topics().newCompleteRealTopic());

        final List<Topic> topics = topicService.getTopics(value);

        assertThat(topics.size()).isEqualTo(3);
    }

    @Test
    public void canIndexTopic() {
        final String value = "value";
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();

        topicService.index(httpTopic, value);

        assertThat(Repositories.tags().getAll().size()).isEqualTo(1);
    }

    @Test
    public void correctlySetTopicIdInTag() {
        final String value = "value";
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();

        topicService.index(httpTopic, value);

        assertThat(Repositories.tags().getAll().get(0).getTopicIds()).contains(httpTopic.getId());
    }


    @Test
    public void correctlySetTopicIdInTagWhenTagUniqueness() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID(), FakeUniqueTopicType.Unique);
        final String tag = "tag-fr";

        topicService.index(fakeTopic, tag);

        assertThat(Repositories.tags().getAll().get(0).getTopicIds()).contains(fakeTopic.getId());
    }

    @Test
    public void doNotAddDoubleUniqueId() {
        final FakeTopic newTopic = new FakeTopic(UUID.randomUUID(), FakeUniqueTopicType.Unique);
        Repositories.topics().add(newTopic);
        final String tag = "tag-fr";
        createTagForFakeUniqueTopic(tag);

        topicService.index(newTopic, tag);

        assertThat(Repositories.tags().getAll().get(0).getTopicIds().size()).isEqualTo(1);
    }

    @Test
    public void canUseExistingTag() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID(), FakeUniqueTopicType.NotUnique);
        final String tag = "tag-fr";

        topicService.index(fakeTopic, tag);

        assertThat(Repositories.tags().getAll().size()).isEqualTo(1);
    }

    @Test
    public void changeCurrentIdIfExistingUniqueTopicType() {
        final String value = "tag";
        final FakeTopic fakeTopic = createTagForFakeUniqueTopic(value);
        final FakeTopic anotherTopic = new FakeTopic(UUID.randomUUID(), FakeUniqueTopicType.Unique);
        Repositories.topics().add(anotherTopic);

        topicService.index(anotherTopic, value);

        final Tag tag = Repositories.tags().getAll().get(0);
        assertThat(tag.getTopicIds().size()).isEqualTo(1);
        assertThat(tag.getTopicIds()).contains(fakeTopic.getId());
        assertThat(anotherTopic.getCurrentId()).isEqualTo(fakeTopic.getId());
    }

    @Test
    public void createTagsForHttpTopic() {
        topicService.createHttpTopic("http://www.url.com");

        final List<Tag> tags = Repositories.tags().getAll();
        assertThat(tags.size()).isEqualTo(8);
    }

    @Test
    public void createTagsForRealTopic() {
        topicService.createRealTopic(FeelhubLanguage.reference(), "ref", RealTopicType.Automobile);

        final List<Tag> tags = Repositories.tags().getAll();
        assertThat(tags.size()).isEqualTo(1);
    }

    @Test
    public void canSpecifyRestrictedMimeType() {
        exception.expect(TopicException.class);

        topicService.createHttpTopic("http://www.url.com", MediaType.IMAGE_ALL);
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


    private Injector injector;
    private TopicService topicService;
    private String canonicalUri;
}
