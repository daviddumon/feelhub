package com.feelhub.domain.topic.usable;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.tag.TagRequestEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.TopicType;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsUsableTopic {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Test
    public void hasAUser() {
        final User fakeActiveUser = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final FakeUsableTopic fakeUsableTopic = new FakeUsableTopic(UUID.randomUUID());

        fakeUsableTopic.setUserId(fakeActiveUser.getId());

        assertThat(fakeUsableTopic.getUserId()).isEqualTo(fakeActiveUser.getId());
    }

    @Test
    public void canAddAName() {
        final FakeUsableTopic fakeUsableTopic = new FakeUsableTopic(UUID.randomUUID());
        final String name = "Name-reference";
        final FeelhubLanguage language = FeelhubLanguage.reference();

        fakeUsableTopic.addName(language, name);

        assertThat(fakeUsableTopic.getName(language)).isEqualTo(name);
    }

    @Test
    public void canReturnEmptyName() {
        final FakeUsableTopic fakeUsableTopic = new FakeUsableTopic(UUID.randomUUID());

        assertThat(fakeUsableTopic.getName(FeelhubLanguage.REFERENCE)).isEmpty();
    }

    @Test
    public void canReturnReferenceNameIfExists() {
        final FakeUsableTopic fakeUsableTopic = new FakeUsableTopic(UUID.randomUUID());
        final String name = "Name-reference";
        fakeUsableTopic.addName(FeelhubLanguage.reference(), name);

        final String frName = fakeUsableTopic.getName(FeelhubLanguage.fromCode("fr"));

        assertThat(frName).isEqualTo(name);
    }

    @Test
    public void canReturnNoneNameIfExists() {
        final FakeUsableTopic fakeUsableTopic = new FakeUsableTopic(UUID.randomUUID());
        final String name = "Name-none";
        fakeUsableTopic.addName(FeelhubLanguage.none(), name);

        final String frName = fakeUsableTopic.getName(FeelhubLanguage.fromCode("fr"));

        assertThat(frName).isEqualTo(name);
    }

    @Test
    public void correctlyFormatNames() {
        final FakeUsableTopic fakeUsableTopic = new FakeUsableTopic(UUID.randomUUID());
        final String name = "nAMe-refeRence";
        final FeelhubLanguage language = FeelhubLanguage.reference();

        fakeUsableTopic.addName(language, name);

        assertThat(fakeUsableTopic.getName(language)).isEqualTo("Name-reference");
    }

    @Test
    public void canAddADescription() {
        final FakeUsableTopic fakeUsableTopic = new FakeUsableTopic(UUID.randomUUID());
        final String description = "My description";
        final FeelhubLanguage language = FeelhubLanguage.reference();

        fakeUsableTopic.addDescription(language, description);

        assertThat(fakeUsableTopic.getDescription(language)).isEqualTo(description);
    }

    @Test
    public void canReturnEmptyDescription() {
        final FakeUsableTopic fakeUsableTopic = new FakeUsableTopic(UUID.randomUUID());

        assertThat(fakeUsableTopic.getDescription(FeelhubLanguage.reference())).isEmpty();
    }

    @Test
    public void returnReferenceDescriptionIfExists() {
        final FakeUsableTopic fakeUsableTopic = new FakeUsableTopic(UUID.randomUUID());
        final String description = "My description";
        fakeUsableTopic.addDescription(FeelhubLanguage.reference(), description);

        final String frDescription = fakeUsableTopic.getDescription(FeelhubLanguage.fromCode("fr"));

        assertThat(frDescription).isEqualTo(description);
    }

    @Test
    public void returnNoneDescriptionIfExists() {
        final FakeUsableTopic fakeUsableTopic = new FakeUsableTopic(UUID.randomUUID());
        final String description = "My description";
        fakeUsableTopic.addDescription(FeelhubLanguage.none(), description);

        final String frDescription = fakeUsableTopic.getDescription(FeelhubLanguage.fromCode("fr"));

        assertThat(frDescription).isEqualTo(description);
    }

    @Test
    public void requestTagCreationWhenAddingDescription() {
        bus.capture(TagRequestEvent.class);
        final FakeUsableTopic fakeUsableTopic = new FakeUsableTopic(UUID.randomUUID());
        final String referenceName = "name-reference";

        fakeUsableTopic.addName(FeelhubLanguage.reference(), referenceName);

        final TagRequestEvent tagRequestEvent = bus.lastEvent(TagRequestEvent.class);
        assertThat(tagRequestEvent).isNotNull();
        assertThat(tagRequestEvent.getUsableTopic()).isEqualTo(fakeUsableTopic);
        assertThat(tagRequestEvent.getName()).isEqualTo(referenceName);
    }

    @Test
    public void hasSubTypes() {
        final FakeUsableTopic fakeUsableTopic = new FakeUsableTopic(UUID.randomUUID());

        assertThat(fakeUsableTopic.getSubTypes()).isNotNull();
        assertThat(fakeUsableTopic.getSubTypes().size()).isZero();
    }

    @Test
    public void canAddASubType() {
        final FakeUsableTopic fakeUsableTopic = new FakeUsableTopic(UUID.randomUUID());
        final String subtype = "subtype";

        fakeUsableTopic.addSubType(subtype);

        assertThat(fakeUsableTopic.getSubTypes().size()).isEqualTo(1);
        assertThat(fakeUsableTopic.getSubTypes().get(0)).isEqualTo(subtype);
    }

    @Test
    public void hasUrls() {
        final FakeUsableTopic fakeUsableTopic = new FakeUsableTopic(UUID.randomUUID());

        assertThat(fakeUsableTopic.getUrls()).isNotNull();
        assertThat(fakeUsableTopic.getUrls().size()).isZero();
    }

    @Test
    public void canAddUrl() {
        final FakeUsableTopic fakeUsableTopic = new FakeUsableTopic(UUID.randomUUID());
        final String url = "http://www.url.com";

        fakeUsableTopic.addUrl(url);

        assertThat(fakeUsableTopic.getUrls().size()).isEqualTo(1);
        assertThat(fakeUsableTopic.getUrls().get(0)).isEqualTo(url);
    }

    @Test
    public void hasAnIllustrationLink() {
        final FakeUsableTopic fakeUsableTopic = new FakeUsableTopic(UUID.randomUUID());

        assertThat(fakeUsableTopic.getIllustrationLink()).isNotNull();
        assertThat(fakeUsableTopic.getIllustrationLink()).isEmpty();
    }

    @Test
    public void canSetAnIllustrationLink() {
        final FakeUsableTopic fakeUsableTopic = new FakeUsableTopic(UUID.randomUUID());
        final String illustrationLink = "link";

        fakeUsableTopic.setIllustrationLink(illustrationLink);

        assertThat(fakeUsableTopic.getIllustrationLink()).isEqualTo(illustrationLink);
    }

    class FakeUsableTopic extends UsableTopic {

        public FakeUsableTopic(final UUID id) {
            super(id);
        }

        @Override
        public TopicType getType() {
            return null;
        }
    }
}
