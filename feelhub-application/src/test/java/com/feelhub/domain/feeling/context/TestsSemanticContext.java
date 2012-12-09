package com.feelhub.domain.feeling.context;

import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.topic.usable.real.RealTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsSemanticContext {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canGetAContextForAKeyword() {
        final SemanticContext semanticContext = new SemanticContext();
        final RealTopic topic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic topic2 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic topic3 = TestFactories.topics().newCompleteRealTopic();
        final Tag word1 = TestFactories.tags().newTag("word1");
        word1.addTopic(topic1);
        final Tag word2 = TestFactories.tags().newTag("word2");
        word2.addTopic(topic2);
        final Tag word3 = TestFactories.tags().newTag("word3");
        word3.addTopic(topic3);
        TestFactories.relations().newRelation(topic1.getId(), topic2.getId());
        TestFactories.relations().newRelation(topic1.getId(), topic3.getId());

        semanticContext.extractFor(topic1);

        assertThat(semanticContext.getValues().size()).isEqualTo(2);
    }
}
