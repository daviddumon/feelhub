package com.feelhub.domain.feeling.analyze;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsSemanticContext {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canGetAContextForAKeyword() {
        final RealTopic topic1 = TestFactories.topics().newCompleteRealTopic("name1");
        final RealTopic topic2 = TestFactories.topics().newCompleteRealTopic("name2");
        final RealTopic topic3 = TestFactories.topics().newCompleteRealTopic("name3");
        TestFactories.tags().newTag("value1", topic2);
        TestFactories.tags().newTag("value2", topic2);
        TestFactories.tags().newTag("value3", topic3);
        TestFactories.related().newRelated(topic1.getId(), topic2.getId());
        TestFactories.related().newRelated(topic1.getId(), topic3.getId());

        final SemanticContext semanticContext = new SemanticContext(topic1.getId(), FeelhubLanguage.reference());

        assertThat(semanticContext.getKnownValues().size()).isEqualTo(3);
        assertThat(semanticContext.getKnownValues().keySet()).contains("value1");
        assertThat(semanticContext.getKnownValues().keySet()).contains("value2");
        assertThat(semanticContext.getKnownValues().keySet()).contains("value3");
    }

    @Test
    public void mediaDoNotBelongToSemanticContext() {
        final RealTopic topic1 = TestFactories.topics().newCompleteRealTopic("name1");
        final RealTopic topic2 = TestFactories.topics().newCompleteRealTopic("name2");
        final RealTopic topic3 = TestFactories.topics().newCompleteRealTopic("name3");
        TestFactories.tags().newTag("value1", topic2);
        TestFactories.tags().newTag("value2", topic2);
        TestFactories.tags().newTag("value3", topic3);
        TestFactories.related().newRelated(topic1.getId(), topic2.getId());
        TestFactories.medias().newMedia(topic1.getId(), topic3.getId());

        final SemanticContext semanticContext = new SemanticContext(topic1.getId(), FeelhubLanguage.reference());

        assertThat(semanticContext.getKnownValues().size()).isEqualTo(2);
        assertThat(semanticContext.getKnownValues().keySet()).contains("value1");
        assertThat(semanticContext.getKnownValues().keySet()).contains("value2");
    }

    @Test
    public void onlyGetTagsWithGoodLanguage() {
        final RealTopic topic1 = TestFactories.topics().newCompleteRealTopic("name1");
        final RealTopic topic2 = TestFactories.topics().newCompleteRealTopic("name2");
        final RealTopic topic3 = TestFactories.topics().newCompleteRealTopic("name3");
        TestFactories.tags().newTag("value1", topic2, FeelhubLanguage.reference());
        TestFactories.tags().newTag("value2", topic2, FeelhubLanguage.fromCode("fr"));
        TestFactories.tags().newTag("value3", topic3, FeelhubLanguage.none());
        TestFactories.related().newRelated(topic1.getId(), topic2.getId());
        TestFactories.related().newRelated(topic1.getId(), topic3.getId());

        final SemanticContext semanticContext = new SemanticContext(topic1.getId(), FeelhubLanguage.fromCode("fr"));

        assertThat(semanticContext.getKnownValues().size()).isEqualTo(1);
        assertThat(semanticContext.getKnownValues().keySet()).contains("value2");
    }
}
