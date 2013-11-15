package com.feelhub.domain.topic;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.feeling.Feeling;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.*;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.google.inject.Injector;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class TopicTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public SystemTime systemTime = SystemTime.fixed();

    @Test
    public void hasAnId() {
        final UUID id = UUID.randomUUID();

        final FakeTopicImplementation topic = new FakeTopicImplementation(id);

        assertThat(topic.getId()).isNotNull();
        assertThat(topic.getId()).isEqualTo(id);
    }

    @Test
    public void hasACurrentId() {
        final UUID id = UUID.randomUUID();

        final FakeTopicImplementation topic = new FakeTopicImplementation(id);

        assertThat(topic.getCurrentId()).isNotNull();
        assertThat(topic.getId()).isEqualTo(id);
        assertThat(topic.getId()).isEqualTo(topic.getCurrentId());
    }

    @Test
    public void canChangeCurrentId() {
        final HttpTopic oldTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Article);
        final UUID oldId = oldTopic.getId();
        final HttpTopic newTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Article);

        oldTopic.changeCurrentId(newTopic.getId());

        assertThat(oldTopic.getId()).isEqualTo(oldId);
        assertThat(oldTopic.getCurrentId()).isEqualTo(newTopic.getId());
        assertThat(oldTopic.getId()).isNotEqualTo(oldTopic.getCurrentId());
    }

    @Test
    public void changeCurrentIdMergeTopics() {
        final HttpTopic oldTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Article);
        final HttpTopic newTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Article);
        final Tag tag = TestFactories.tags().newTag("tag");
        tag.addTopic(oldTopic, FeelhubLanguage.reference());

        oldTopic.changeCurrentId(newTopic.getId());

        assertThat(tag.getTopicsIdFor(FeelhubLanguage.reference())).contains(newTopic.getId());
    }

    @Test
    public void mergeOnlyIfDifferent() {
        final HttpTopic topic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Article);
        final TopicMerger topicMerger = mock(TopicMerger.class);
        topic.setTopicMerger(topicMerger);

        topic.changeCurrentId(topic.getId());

        verify(topicMerger, never()).merge(any(UUID.class), any(UUID.class));
    }

    @Test
    public void hasAUser() {
        final User fakeActiveUser = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final FakeTopicImplementation topic = new FakeTopicImplementation(UUID.randomUUID());

        topic.setUserId(fakeActiveUser.getId());

        assertThat(topic.getUserId()).isEqualTo(fakeActiveUser.getId());
    }

    @Test
    public void canAddAName() {
        final FakeTopicImplementation topic = new FakeTopicImplementation(UUID.randomUUID());
        final String name = "Name-reference";
        final FeelhubLanguage language = FeelhubLanguage.reference();

        topic.addName(language, name);

        assertThat(topic.getName(language)).isEqualTo(name);
    }

    @Test
    public void canReturnEmptyName() {
        final FakeTopicImplementation topic = new FakeTopicImplementation(UUID.randomUUID());

        assertThat(topic.getName(FeelhubLanguage.REFERENCE)).isEmpty();
    }

    @Test
    public void canReturnReferenceNameIfExists() {
        final FakeTopicImplementation topic = new FakeTopicImplementation(UUID.randomUUID());
        final String name = "Name-reference";
        topic.addName(FeelhubLanguage.reference(), name);

        final String frName = topic.getName(FeelhubLanguage.fromCode("fr"));

        assertThat(frName).isEqualTo(name);
    }

    @Test
    public void canReturnNoneNameIfExists() {
        final FakeTopicImplementation topic = new FakeTopicImplementation(UUID.randomUUID());
        final String name = "Name-none";
        topic.addName(FeelhubLanguage.none(), name);

        final String frName = topic.getName(FeelhubLanguage.fromCode("fr"));

        assertThat(frName).isEqualTo(name);
    }

    @Test
    public void returnAnyNamesFinally() {
        final FakeTopicImplementation topic = new FakeTopicImplementation(UUID.randomUUID());
        final String name = "Name-fr";
        topic.addName(FeelhubLanguage.fromCode("fr"), name);

        final String anyName = topic.getName(FeelhubLanguage.reference());

        assertThat(anyName).isEqualTo(name);
    }

    @Test
    public void correctlyFormatNames() {
        final FakeTopicImplementation topic = new FakeTopicImplementation(UUID.randomUUID());
        final String name = "nAMe-refeRence";
        final FeelhubLanguage language = FeelhubLanguage.reference();

        topic.addName(language, name);

        assertThat(topic.getName(language)).isEqualTo("Name-reference");
    }

    @Test
    public void canAddADescription() {
        final FakeTopicImplementation topic = new FakeTopicImplementation(UUID.randomUUID());
        final String description = "My description";
        final FeelhubLanguage language = FeelhubLanguage.reference();

        topic.addDescription(language, description);

        assertThat(topic.getDescription(language)).isEqualTo(description);
    }

    @Test
    public void canReturnEmptyDescription() {
        final FakeTopicImplementation topic = new FakeTopicImplementation(UUID.randomUUID());

        assertThat(topic.getDescription(FeelhubLanguage.reference())).isEmpty();
    }

    @Test
    public void returnReferenceDescriptionIfExists() {
        final FakeTopicImplementation topic = new FakeTopicImplementation(UUID.randomUUID());
        final String description = "My description";
        topic.addDescription(FeelhubLanguage.reference(), description);

        final String frDescription = topic.getDescription(FeelhubLanguage.fromCode("fr"));

        assertThat(frDescription).isEqualTo(description);
    }

    @Test
    public void returnNoneDescriptionIfExists() {
        final FakeTopicImplementation topic = new FakeTopicImplementation(UUID.randomUUID());
        final String description = "My description";
        topic.addDescription(FeelhubLanguage.none(), description);

        final String frDescription = topic.getDescription(FeelhubLanguage.fromCode("fr"));

        assertThat(frDescription).isEqualTo(description);
    }

    @Test
    public void hasSubTypes() {
        final FakeTopicImplementation topic = new FakeTopicImplementation(UUID.randomUUID());

        assertThat(topic.getSubTypes()).isNotNull();
        assertThat(topic.getSubTypes().size()).isZero();
    }

    @Test
    public void canAddASubType() {
        final FakeTopicImplementation topic = new FakeTopicImplementation(UUID.randomUUID());
        final String subtype = "subtype";

        topic.addSubType(subtype);

        assertThat(topic.getSubTypes().size()).isEqualTo(1);
        assertThat(topic.getSubTypes().get(0)).isEqualTo(subtype);
    }

    @Test
    public void hasUris() {
        final FakeTopicImplementation topic = new FakeTopicImplementation(UUID.randomUUID());

        assertThat(topic.getUris()).isNotNull();
        assertThat(topic.getUris().size()).isZero();
    }

    @Test
    public void canAddUri() {
        final FakeTopicImplementation topic = new FakeTopicImplementation(UUID.randomUUID());
        final Uri uri = new Uri("http://www.url.com");

        topic.addUri(uri);

        assertThat(topic.getUris().size()).isEqualTo(1);
        assertThat(topic.getUris().get(0)).isEqualTo(uri);
    }

    @Test
    public void canSetThumbnail() {
        final FakeTopicImplementation topic = new FakeTopicImplementation(UUID.randomUUID());
        final String thumbnail = "link";

        topic.setThumbnail(thumbnail);

        assertThat(topic.getThumbnail()).isEqualTo(thumbnail);
    }

    @Test
    public void keepThumbnailsCollection() {
        final FakeTopicImplementation topic = new FakeTopicImplementation(UUID.randomUUID());

        assertThat(topic.getThumbnails()).isNotNull();
        assertThat(topic.getThumbnails()).isEmpty();
    }

    @Test
    public void canAddAThumbnail() {
        final Thumbnail thumbnail = new Thumbnail();
        final FakeTopicImplementation topic = new FakeTopicImplementation(UUID.randomUUID());

        topic.addThumbnail(thumbnail);

        assertThat(topic.getThumbnails().size()).isEqualTo(1);
    }

    @Test
    public void aTopicCanIncreaseHappyFeelingCount() {
        final UUID id = UUID.randomUUID();
        final FakeTopicImplementation topic = new FakeTopicImplementation(id);
        final Feeling feeling = TestFactories.feelings().happyFeeling(topic);

        topic.increasesFeelingCount(feeling);

        assertThat(topic.getHappyFeelingCount()).isEqualTo(1);
    }

    @Test
    public void aTopicCanIncreaseSadFeelingCount() {
        final UUID id = UUID.randomUUID();
        final FakeTopicImplementation topic = new FakeTopicImplementation(id);
        final Feeling feeling = TestFactories.feelings().sadFeeling(topic);

        topic.increasesFeelingCount(feeling);

        assertThat(topic.getSadFeelingCount()).isEqualTo(1);
    }

    @Test
    public void aTopicCanIncreaseBoredFeelingCount() {
        final UUID id = UUID.randomUUID();
        final FakeTopicImplementation topic = new FakeTopicImplementation(id);
        final Feeling feeling = TestFactories.feelings().boredFeeling(topic);

        topic.increasesFeelingCount(feeling);

        assertThat(topic.getBoredFeelingCount()).isEqualTo(1);
    }

    @Test
    public void increasesFeelingSetHasFeelingToTrue() {
        final UUID id = UUID.randomUUID();
        final FakeTopicImplementation topic = new FakeTopicImplementation(id);
        final Feeling feeling = TestFactories.feelings().boredFeeling(topic);

        topic.increasesFeelingCount(feeling);

        assertThat(topic.getHasFeelings()).isEqualTo(true);
    }

    @Test
    public void defaultHasFeelingsIsFalse() {
        final UUID id = UUID.randomUUID();
        final FakeTopicImplementation topic = new FakeTopicImplementation(id);

        assertThat(topic.getHasFeelings()).isEqualTo(false);
    }

    @Test
    public void canIncrementViewCount() {
        final UUID id = UUID.randomUUID();
        final FakeTopicImplementation topic = new FakeTopicImplementation(id);
        topic.incrementViewCount();

        assertThat(topic.getViewCount()).isEqualTo(1);
    }

    @Test
    public void topicHasAPopularity() {
        final UUID id = UUID.randomUUID();
        final FakeTopicImplementation topic = new FakeTopicImplementation(id);

        assertThat(topic.getPopularity()).isEqualTo(1);
    }

    @Test
    public void incrementViewCountIncrementPopularity() {
        final UUID id = UUID.randomUUID();
        final FakeTopicImplementation topic = new FakeTopicImplementation(id);

        topic.incrementViewCount();

        assertThat(topic.getPopularity()).isEqualTo(1);
    }

    @Test
    public void incrementFeelingCountIncrementPopularity() {
        final UUID id = UUID.randomUUID();
        final FakeTopicImplementation topic = new FakeTopicImplementation(id);
        final Feeling feeling = TestFactories.feelings().boredFeeling(topic);

        topic.increasesFeelingCount(feeling);

        assertThat(topic.getPopularity()).isEqualTo(1);
    }

    @Test
    public void popularityIs1Under10() {
        final UUID id = UUID.randomUUID();
        final FakeTopicImplementation topic = new FakeTopicImplementation(id);

        for (int i = 0; i < 8; i++) {
            topic.incrementViewCount();
        }

        assertThat(topic.getPopularity()).isEqualTo(1);
    }

    @Test
    public void popularityIs2Under20() {
        final UUID id = UUID.randomUUID();
        final FakeTopicImplementation topic = new FakeTopicImplementation(id);

        for (int i = 0; i < 18; i++) {
            topic.incrementViewCount();
        }

        assertThat(topic.getPopularity()).isEqualTo(2);
    }

    @Test
    public void popularityIs3Under50() {
        final UUID id = UUID.randomUUID();
        final FakeTopicImplementation topic = new FakeTopicImplementation(id);

        for (int i = 0; i < 48; i++) {
            topic.incrementViewCount();
        }

        assertThat(topic.getPopularity()).isEqualTo(3);
    }

    @Test
    public void popularityIs4Under100() {
        final UUID id = UUID.randomUUID();
        final FakeTopicImplementation topic = new FakeTopicImplementation(id);

        for (int i = 0; i < 98; i++) {
            topic.incrementViewCount();
        }

        assertThat(topic.getPopularity()).isEqualTo(4);
    }

    @Test
    public void popularityIs5Over100() {
        final UUID id = UUID.randomUUID();
        final FakeTopicImplementation topic = new FakeTopicImplementation(id);

        for (int i = 0; i < 148; i++) {
            topic.incrementViewCount();
        }

        assertThat(topic.getPopularity()).isEqualTo(5);
    }

    class FakeTopicImplementation extends Topic {

        public FakeTopicImplementation(final UUID id) {
            super(id);
        }

        @Override
        public TopicType getType() {
            return null;
        }

        @Override
        public String getTypeValue() {
            return null;
        }
    }

    private Injector injector;
}
