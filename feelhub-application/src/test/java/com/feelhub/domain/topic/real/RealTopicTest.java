package com.feelhub.domain.topic.real;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.SystemTime;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class RealTopicTest {

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
    public void onlyRequestTranslationForTranslableTopics() {
        final RealTopic topic = new RealTopic(UUID.randomUUID(), untranslatableType());

        topic.addName(FeelhubLanguage.fromCode("es"), "Description-es");

        assertThat(topic.mustTranslate()).isFalse();
    }

    @Test
    public void onlyRequestTranslationIfTranslationNeeded() {
        final RealTopic anotherTopic = new RealTopic(UUID.randomUUID(), translatableType());
        final String name = "Description-language";

        anotherTopic.addName(FeelhubLanguage.REFERENCE, name);

        assertThat(anotherTopic.mustTranslate()).isFalse();
    }

    @Test
    public void doNotTranslateForReferenceLanguage() {
        final RealTopic topic = new RealTopic(UUID.randomUUID(), translatableType());

        topic.addName(FeelhubLanguage.reference(), "Description-reference");

        assertThat(topic.mustTranslate()).isFalse();
    }

    @Test
    public void doNotTranslateForNoneLanguage() {
        final RealTopic topic = new RealTopic(UUID.randomUUID(), translatableType());

        topic.addName(FeelhubLanguage.none(), "Description-reference");

        assertThat(topic.mustTranslate()).isFalse();
    }

    private RealTopicType translatableType() {
        return RealTopicType.Anniversary;
    }

    private RealTopicType untranslatableType() {
        return RealTopicType.Automobile;
    }

}
