package com.feelhub.domain.tag;

import com.feelhub.domain.topic.usable.real.RealTopic;
import com.feelhub.domain.topic.usable.web.WebTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsTag {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canAddARealTopic() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Tag tag = new Tag("value");

        tag.addTopic(realTopic);

        assertThat(tag.getTopicIds()).isNotNull();
        assertThat(tag.getTopicIds().size()).isEqualTo(1);
        assertThat(tag.getTopicIds().get(0)).isEqualTo(realTopic.getId());
    }

    @Test
    public void canAddAWebTopic() {
        final WebTopic webTopic = TestFactories.topics().newCompleteWebTopic();
        final Tag tag = new Tag("value");

        tag.addTopic(webTopic);

        assertThat(tag.getTopicIds()).isNotNull();
        assertThat(tag.getTopicIds().size()).isEqualTo(1);
        assertThat(tag.getTopicIds().get(0)).isEqualTo(webTopic.getId());
    }
}
