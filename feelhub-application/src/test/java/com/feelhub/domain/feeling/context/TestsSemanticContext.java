package com.feelhub.domain.feeling.context;

import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
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
        final Tag word1 = TestFactories.tags().newWord("word1", FeelhubLanguage.reference());
        final Tag word2 = TestFactories.tags().newWord("word2", FeelhubLanguage.reference());
        final Tag word3 = TestFactories.tags().newWord("word3", FeelhubLanguage.reference());
        final Tag word4 = TestFactories.tags().newWord("word4", FeelhubLanguage.fromCode("fr"));
        TestFactories.relations().newRelation(word1.getTopicId(), word2.getTopicId());
        TestFactories.relations().newRelation(word1.getTopicId(), word3.getTopicId());
        TestFactories.relations().newRelation(word1.getTopicId(), word4.getTopicId());

        semanticContext.extractFor(word1.getValue(), word1.getLanguage());

        assertThat(semanticContext.getValues().size()).isEqualTo(2);
    }
}
