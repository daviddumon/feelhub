package com.feelhub.domain.feeling.analyze;

import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

public class TestsSemanticContext {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canGetAContextForAKeyword() {
        final RealTopic topic1 = TestFactories.topics().newCompleteRealTopic("name1");
        final RealTopic topic2 = TestFactories.topics().newCompleteRealTopic("name2");
        final RealTopic topic3 = TestFactories.topics().newCompleteRealTopic("name3");
        TestFactories.relations().newRelated(topic1.getId(), topic2.getId());
        TestFactories.relations().newRelated(topic1.getId(), topic3.getId());

        final SemanticContext semanticContext = new SemanticContext(topic1);

    }
}
