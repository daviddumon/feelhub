package com.feelhub.domain.topic;

import com.feelhub.domain.meta.Illustration;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsTopic {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

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
        final UUID id = UUID.randomUUID();
        final FakeTopic fakeTopic = new FakeTopic(id);
        final UUID newId = UUID.randomUUID();

        fakeTopic.changeCurrentId(newId);

        assertThat(fakeTopic.getId()).isEqualTo(id);
        assertThat(fakeTopic.getCurrentId()).isEqualTo(newId);
        assertThat(fakeTopic.getId()).isNotEqualTo(fakeTopic.getCurrentId());
    }

    @Test
    public void changeCurrentIdMergeTopics() {
        final FakeTopic oldTopic = new FakeTopic(UUID.randomUUID());
        final Illustration illustration = TestFactories.illustrations().newIllustration(oldTopic.getId());
        final FakeTopic newTopic = new FakeTopic(UUID.randomUUID());

        oldTopic.changeCurrentId(newTopic.getId());

        assertThat(illustration.getTopicId()).isEqualTo(newTopic.getId());
    }

    class FakeTopic extends Topic {

        public FakeTopic(final UUID id) {
            super(id);
        }
    }
}
