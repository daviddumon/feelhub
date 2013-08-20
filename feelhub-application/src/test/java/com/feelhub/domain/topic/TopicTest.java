package com.feelhub.domain.topic;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.*;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.google.inject.Injector;
import org.joda.time.DateTime;
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

        final FakeTopic fakeTopic = new FakeTopic(id);

        assertThat(fakeTopic.getId()).isNotNull();
        assertThat(fakeTopic.getId()).isEqualTo(id);
    }

    @Test
    public void hasACurrentId() {
        final UUID id = UUID.randomUUID();

        final FakeTopic fakeTopic = new FakeTopic(id);

        assertThat(fakeTopic.getCurrentId()).isNotNull();
        assertThat(fakeTopic.getId()).isEqualTo(id);
        assertThat(fakeTopic.getId()).isEqualTo(fakeTopic.getCurrentId());
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
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());

        fakeTopic.setUserId(fakeActiveUser.getId());

        assertThat(fakeTopic.getUserId()).isEqualTo(fakeActiveUser.getId());
    }

    @Test
    public void canAddAName() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());
        final String name = "Name-reference";
        final FeelhubLanguage language = FeelhubLanguage.reference();

        fakeTopic.addName(language, name);

        assertThat(fakeTopic.getName(language)).isEqualTo(name);
    }

    @Test
    public void canReturnEmptyName() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());

        assertThat(fakeTopic.getName(FeelhubLanguage.REFERENCE)).isEmpty();
    }

    @Test
    public void canReturnReferenceNameIfExists() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());
        final String name = "Name-reference";
        fakeTopic.addName(FeelhubLanguage.reference(), name);

        final String frName = fakeTopic.getName(FeelhubLanguage.fromCode("fr"));

        assertThat(frName).isEqualTo(name);
    }

    @Test
    public void canReturnNoneNameIfExists() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());
        final String name = "Name-none";
        fakeTopic.addName(FeelhubLanguage.none(), name);

        final String frName = fakeTopic.getName(FeelhubLanguage.fromCode("fr"));

        assertThat(frName).isEqualTo(name);
    }

    @Test
    public void returnAnyNamesFinally() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());
        final String name = "Name-fr";
        fakeTopic.addName(FeelhubLanguage.fromCode("fr"), name);

        final String anyName = fakeTopic.getName(FeelhubLanguage.reference());

        assertThat(anyName).isEqualTo(name);
    }

    @Test
    public void correctlyFormatNames() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());
        final String name = "nAMe-refeRence";
        final FeelhubLanguage language = FeelhubLanguage.reference();

        fakeTopic.addName(language, name);

        assertThat(fakeTopic.getName(language)).isEqualTo("Name-reference");
    }

    @Test
    public void canAddADescription() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());
        final String description = "My description";
        final FeelhubLanguage language = FeelhubLanguage.reference();

        fakeTopic.addDescription(language, description);

        assertThat(fakeTopic.getDescription(language)).isEqualTo(description);
    }

    @Test
    public void canReturnEmptyDescription() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());

        assertThat(fakeTopic.getDescription(FeelhubLanguage.reference())).isEmpty();
    }

    @Test
    public void returnReferenceDescriptionIfExists() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());
        final String description = "My description";
        fakeTopic.addDescription(FeelhubLanguage.reference(), description);

        final String frDescription = fakeTopic.getDescription(FeelhubLanguage.fromCode("fr"));

        assertThat(frDescription).isEqualTo(description);
    }

    @Test
    public void returnNoneDescriptionIfExists() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());
        final String description = "My description";
        fakeTopic.addDescription(FeelhubLanguage.none(), description);

        final String frDescription = fakeTopic.getDescription(FeelhubLanguage.fromCode("fr"));

        assertThat(frDescription).isEqualTo(description);
    }

    @Test
    public void hasSubTypes() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());

        assertThat(fakeTopic.getSubTypes()).isNotNull();
        assertThat(fakeTopic.getSubTypes().size()).isZero();
    }

    @Test
    public void canAddASubType() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());
        final String subtype = "subtype";

        fakeTopic.addSubType(subtype);

        assertThat(fakeTopic.getSubTypes().size()).isEqualTo(1);
        assertThat(fakeTopic.getSubTypes().get(0)).isEqualTo(subtype);
    }

    @Test
    public void hasUris() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());

        assertThat(fakeTopic.getUris()).isNotNull();
        assertThat(fakeTopic.getUris().size()).isZero();
    }

    @Test
    public void canAddUri() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());
        final Uri uri = new Uri("http://www.url.com");

        fakeTopic.addUri(uri);

        assertThat(fakeTopic.getUris().size()).isEqualTo(1);
        assertThat(fakeTopic.getUris().get(0)).isEqualTo(uri);
    }

    @Test
    public void canSetThumbnail() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());
        final String thumbnail = "link";

        fakeTopic.setThumbnail(thumbnail);

        assertThat(fakeTopic.getThumbnail()).isEqualTo(thumbnail);
    }

    @Test
    public void hasADefaultFeelingScore() {
        final RealTopic topic = TestFactories.topics().newCompleteRealTopic();

        assertThat(topic.getFeelingScore()).isEqualTo(0);
    }

    @Test
    public void hasAFeelingScore() {
        systemTime.set(new DateTime(10));
        final RealTopic topic = TestFactories.topics().newCompleteRealTopic();

        systemTime.set(new DateTime(11));
        TestFactories.feelings().badFeeling(topic);

        assertThat(topic.getFeelingScore()).isEqualTo(-100);
    }

    @Test
    public void keepThumbnailsCollection() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());

        assertThat(fakeTopic.getThumbnails()).isNotNull();
        assertThat(fakeTopic.getThumbnails()).isEmpty();
    }

    @Test
    public void canAddAThumbnail() {
        final Thumbnail thumbnail = new Thumbnail();
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());

        fakeTopic.addThumbnail(thumbnail);

        assertThat(fakeTopic.getThumbnails().size()).isEqualTo(1);
    }

    class FakeTopic extends Topic {

        public FakeTopic(final UUID id) {
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
