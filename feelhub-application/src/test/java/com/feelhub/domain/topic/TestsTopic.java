package com.feelhub.domain.topic;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.tag.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsTopic {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

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
        tag.addTopic(oldTopic);

        oldTopic.changeCurrentId(newTopic.getId());

        assertThat(tag.getTopicIds()).contains(newTopic.getId());
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
    public void hasUrls() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());

        assertThat(fakeTopic.getUrls()).isNotNull();
        assertThat(fakeTopic.getUrls().size()).isZero();
    }

    @Test
    public void canAddUrl() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());
        final String url = "http://www.url.com";

        fakeTopic.addUrl(url);

        assertThat(fakeTopic.getUrls().size()).isEqualTo(1);
        assertThat(fakeTopic.getUrls().get(0)).isEqualTo(url);
    }

    @Test
    public void hasAnIllustrationLink() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());

        assertThat(fakeTopic.getIllustrationLink()).isNotNull();
        assertThat(fakeTopic.getIllustrationLink()).isEmpty();
    }

    @Test
    public void canSetAnIllustrationLink() {
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());
        final String illustrationLink = "link";

        fakeTopic.setIllustrationLink(illustrationLink);

        assertThat(fakeTopic.getIllustrationLink()).isEqualTo(illustrationLink);
    }

    @Test
    public void canCreateTags() {
        bus.capture(TagRequestEvent.class);
        final FakeTopic fakeTopic = new FakeTopic(UUID.randomUUID());
        final String referenceName = "name-reference";

        fakeTopic.createTags(referenceName);

        final TagRequestEvent tagRequestEvent = bus.lastEvent(TagRequestEvent.class);
        assertThat(tagRequestEvent).isNotNull();
        assertThat(tagRequestEvent.getTopic()).isEqualTo(fakeTopic);
        assertThat(tagRequestEvent.getName()).isEqualTo(referenceName);
    }

    class FakeTopic extends Topic {

        public FakeTopic(final UUID id) {
            super(id);
        }

        @Override
        public TopicType getType() {
            return null;
        }
    }
}
