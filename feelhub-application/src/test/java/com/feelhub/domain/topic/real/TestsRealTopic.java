package com.feelhub.domain.topic.real;

import com.feelhub.domain.bingsearch.BingRequest;
import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.translation.ReferenceTranslationRequestEvent;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.SystemTime;
import com.google.common.eventbus.Subscribe;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsRealTopic {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Test
    public void canCreateARealTopic() {
        final UUID id = UUID.randomUUID();
        final RealTopicType type = RealTopicType.Automobile;

        final RealTopic realTopic = new RealTopic(id, type);

        assertThat(realTopic.getId()).isEqualTo(id);
        assertThat(realTopic.getCurrentId()).isEqualTo(id);
        assertThat(realTopic.getType()).isEqualTo(type);
    }

    @Test
    public void requestTranslationWhenAddingName() {
        bus.capture(ReferenceTranslationRequestEvent.class);
        final RealTopic topic = new RealTopic(UUID.randomUUID(), translatableType());

        topic.addName(FeelhubLanguage.fromCode("es"), "Description-es");

        final ReferenceTranslationRequestEvent referenceTranslationRequestEvent = bus.lastEvent(ReferenceTranslationRequestEvent.class);
        assertThat(referenceTranslationRequestEvent).isNotNull();
        assertThat(referenceTranslationRequestEvent.getRealTopic()).isEqualTo(topic);
    }

    @Test
    public void onlyRequestTranslationForTranslableTopics() {
        bus.capture(ReferenceTranslationRequestEvent.class);
        final RealTopic topic = new RealTopic(UUID.randomUUID(), untranslatableType());

        topic.addName(FeelhubLanguage.fromCode("es"), "Description-es");

        final ReferenceTranslationRequestEvent referenceTranslationRequestEvent = bus.lastEvent(ReferenceTranslationRequestEvent.class);
        assertThat(referenceTranslationRequestEvent).isNull();
    }

    @Test
    public void onlyRequestTranslationIfTranslationNeeded() {
        new FakeRealTopicTranslator();
        bus.capture(ReferenceTranslationRequestEvent.class);
        final RealTopic anotherTopic = new RealTopic(UUID.randomUUID(), translatableType());
        final FeelhubLanguage language = FeelhubLanguage.fromCode("fr");
        final String name = "Description-language";

        anotherTopic.addName(language, name);
        anotherTopic.addName(FeelhubLanguage.fromCode("es"), "Description-es");

        final ReferenceTranslationRequestEvent referenceTranslationRequestEvent = bus.lastEvent(ReferenceTranslationRequestEvent.class);
        assertThat(referenceTranslationRequestEvent.getFeelhubLanguage()).isEqualTo(language);
        assertThat(referenceTranslationRequestEvent.getName()).isEqualTo(name);
    }

    @Test
    public void doNotTranslateForReferenceLanguage() {
        bus.capture(ReferenceTranslationRequestEvent.class);
        final RealTopic topic = new RealTopic(UUID.randomUUID(), translatableType());

        topic.addName(FeelhubLanguage.reference(), "Description-reference");

        final ReferenceTranslationRequestEvent referenceTranslationRequestEvent = bus.lastEvent(ReferenceTranslationRequestEvent.class);
        assertThat(referenceTranslationRequestEvent).isNull();
    }

    @Test
    public void doNotTranslateForNoneLanguage() {
        bus.capture(ReferenceTranslationRequestEvent.class);
        final RealTopic topic = new RealTopic(UUID.randomUUID(), translatableType());

        topic.addName(FeelhubLanguage.none(), "Description-reference");

        final ReferenceTranslationRequestEvent referenceTranslationRequestEvent = bus.lastEvent(ReferenceTranslationRequestEvent.class);
        assertThat(referenceTranslationRequestEvent).isNull();
    }

    @Test
    public void fetchImagesWhenAddingName() {
        bus.capture(BingRequest.class);
        final RealTopic topic = new RealTopic(UUID.randomUUID(), untranslatableType());

        topic.addName(FeelhubLanguage.fromCode("es"), "Description-es");

        final BingRequest bingRequest = bus.lastEvent(BingRequest.class);
        assertThat(bingRequest).isNotNull();
        assertThat(bingRequest.getQuery()).isEqualTo("Description-es");
        assertThat(bingRequest.getTopicId()).isEqualTo(topic.getId());
    }

    private RealTopicType translatableType() {
        return RealTopicType.Anniversary;
    }

    private RealTopicType untranslatableType() {
        return RealTopicType.Automobile;
    }

    class FakeRealTopicTranslator {

        FakeRealTopicTranslator() {
            DomainEventBus.INSTANCE.register(this);
        }

        @Subscribe
        public void onRequestTranslationEvent(final ReferenceTranslationRequestEvent referenceTranslationRequestEvent) {
            final RealTopic realTopic = referenceTranslationRequestEvent.getRealTopic();
            realTopic.addName(FeelhubLanguage.reference(), "name-reference");
        }
    }
}
