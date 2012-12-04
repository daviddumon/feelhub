package com.feelhub.domain.tag;

import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsTag {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canAddATopic() {
        final Topic topic = TestFactories.topics().newTopic();
        final Tag tag = new Tag("value");

        tag.addTopic(topic);

        assertThat(tag.getTopicIds()).isNotNull();
        assertThat(tag.getTopicIds().size()).isEqualTo(1);
        assertThat(tag.getTopicIds().get(0)).isEqualTo(topic.getId());
    }
}
