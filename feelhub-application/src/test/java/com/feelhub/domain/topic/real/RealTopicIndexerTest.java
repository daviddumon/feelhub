package com.feelhub.domain.topic.real;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class RealTopicIndexerTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canTagTopic() {
        final RealTopic realTopic = new RealTopic(UUID.randomUUID(), RealTopicType.Other);
        realTopic.addName(FeelhubLanguage.REFERENCE, "test");

        new RealTopicIndexer().index(realTopic);

        assertThat(Repositories.tags().getAll()).hasSize(1).onProperty("id").contains("test");

    }
}
