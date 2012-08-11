package com.steambeat.domain.opinion;

import com.steambeat.domain.topic.Topic;
import com.steambeat.test.TestFactories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsJudgment {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void hasASubjectAndFeeling() {
        final Topic topic = TestFactories.topics().newTopic();
        final Feeling feeling = Feeling.good;

        final Judgment judgment = new Judgment(topic, feeling);

        assertThat(judgment, notNullValue());
        assertThat(judgment.getTopic(), is(topic));
        assertThat(judgment.getFeeling(), is(feeling));
    }
}
