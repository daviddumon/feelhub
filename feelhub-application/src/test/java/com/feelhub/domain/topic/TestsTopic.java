package com.feelhub.domain.topic;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.meta.Illustration;
import com.feelhub.domain.tag.TagRequestEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.translation.ReferenceTranslatioRequestEvent;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import org.joda.time.DateTime;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsTopic {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        id = UUID.randomUUID();
        topic = new Topic(id);
    }

    @Test
    public void canCreateATopic() {
        assertThat(topic.getId()).isNotNull();
        assertThat(topic.getId()).isEqualTo(id);
        assertThat(topic.getCreationDate()).isEqualTo(time.getNow());
        assertThat(topic.getCreationDate()).isEqualTo(topic.getLastModificationDate());
    }

    @Test
    public void canAddAUser() {
        final User fakeActiveUser = TestFactories.users().createFakeActiveUser("mail@mail.com");

        topic.setUserId(fakeActiveUser.getId());

        assertThat(topic.getUserId()).isEqualTo(fakeActiveUser.getId());
    }

    @Test
    public void canSetLastModificationDate() {
        time.waitHours(1);

        topic.setLastModificationDate(new DateTime());

        assertThat(topic.getLastModificationDate()).isEqualTo(time.getNow());
    }

    @Test
    public void canSetType() {
        topic.setType(TopicType.World);

        assertThat(topic.getType()).isEqualTo(TopicType.World);
    }

    @Test
    public void hasSubTypes() {
        assertThat(topic.getSubTypes()).isNotNull();
        assertThat(topic.getSubTypes().size()).isZero();
    }

    @Test
    public void canAddASubType() {
        final String subtype = "subtype";

        topic.addSubType(subtype);

        assertThat(topic.getSubTypes().size()).isEqualTo(1);
        assertThat(topic.getSubTypes().get(0)).isEqualTo(subtype);
    }

    @Test
    public void hasUrls() {
        assertThat(topic.getUrls()).isNotNull();
        assertThat(topic.getUrls().size()).isZero();
    }

    @Test
    public void canAddAnUrl() {
        final String url = "http://www.url.com";

        topic.addUrl(url);

        assertThat(topic.getUrls().size()).isEqualTo(1);
        assertThat(topic.getUrls().get(0)).isEqualTo(url);
    }

    @Test
    public void canAddDescription() {
        final String referenceDescription = "description-reference";

        topic.addDescription(FeelhubLanguage.reference(), referenceDescription);

        assertThat(topic.getDescriptions()).isNotNull();
        assertThat(topic.getDescriptions().size()).isEqualTo(1);
    }

    @Test
    public void requestTagCreationWhenAddingDescription() {
        bus.capture(TagRequestEvent.class);
        final String referenceDescription = "description-reference";

        topic.addDescription(FeelhubLanguage.reference(), referenceDescription);

        final TagRequestEvent tagRequestEvent = bus.lastEvent(TagRequestEvent.class);
        assertThat(tagRequestEvent).isNotNull();
        assertThat(tagRequestEvent.getTopic()).isEqualTo(topic);
    }

    @Test
    public void canReturnGoodDescription() {
        final String referenceDescription = "Description-reference";
        final String frDescription = "Description-fr";
        topic.addDescription(FeelhubLanguage.reference(), referenceDescription);
        topic.addDescription(FeelhubLanguage.fromCode("fr"), frDescription);

        assertThat(topic.getDescription(FeelhubLanguage.reference())).isEqualTo(referenceDescription);
        assertThat(topic.getDescription(FeelhubLanguage.fromCode("fr"))).isEqualTo(frDescription);
        assertThat(topic.getDescription(FeelhubLanguage.fromCode("es"))).isEqualTo(referenceDescription);
    }

    @Test
    public void aTopicHasACurrentTopic() {
        assertThat(topic.getCurrentTopicId()).isEqualTo(topic.getId());
    }

    @Test
    public void canChangeCurrentTopicId() {
        final UUID currentTopicId = UUID.randomUUID();

        topic.changeCurrentTopicId(currentTopicId);

        assertThat(topic.getCurrentTopicId()).isEqualTo(currentTopicId);
    }

    @Test
    public void changeCurrentTopicIdMergeTopics() {
        final Topic oldTopic = TestFactories.topics().newTopic();
        final Illustration illustration = TestFactories.illustrations().newIllustration(oldTopic.getId());
        final Topic newTopic = TestFactories.topics().newTopic();

        oldTopic.changeCurrentTopicId(newTopic.getId());

        assertThat(illustration.getTopicId()).isEqualTo(newTopic.getId());
    }

    @Test
    public void requestTranslationForTranslatableType() {
        bus.capture(ReferenceTranslatioRequestEvent.class);
        topic.addDescription(FeelhubLanguage.fromCode("es"), "Description-es");

        topic.setType(TopicType.Anniversary);

        final ReferenceTranslatioRequestEvent referenceTranslatioRequestEvent = bus.lastEvent(ReferenceTranslatioRequestEvent.class);
        assertThat(referenceTranslatioRequestEvent).isNotNull();
        assertThat(referenceTranslatioRequestEvent.getTopic()).isEqualTo(topic);
    }

    @Test
    public void doNotRequestTranslationIfReferenceDescription() {
        bus.capture(ReferenceTranslatioRequestEvent.class);
        topic.addDescription(FeelhubLanguage.REFERENCE, "Description-es");

        topic.setType(TopicType.Anniversary);

        final ReferenceTranslatioRequestEvent referenceTranslatioRequestEvent = bus.lastEvent(ReferenceTranslatioRequestEvent.class);
        assertThat(referenceTranslatioRequestEvent).isNull();
    }

    @Test
    public void formatDescription() {
        final String description = "DESCripTion";

        topic.addDescription(FeelhubLanguage.fromCode("es"), description);

        assertThat(topic.getDescription(FeelhubLanguage.fromCode("es"))).isEqualTo("Description");
    }

    private UUID id;
    private Topic topic;
}
