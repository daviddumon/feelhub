package com.feelhub.domain.feeling.context;

import com.feelhub.domain.keyword.word.Word;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.fest.assertions.Assertions.assertThat;

public class TestsContext {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canGetAContextForAKeyword() {
        final Word word1 = TestFactories.keywords().newWord("word1", FeelhubLanguage.reference());
        final Word word2 = TestFactories.keywords().newWord("word2", FeelhubLanguage.reference());
        final Word word3 = TestFactories.keywords().newWord("word3", FeelhubLanguage.reference());
        final Word word4 = TestFactories.keywords().newWord("word4", FeelhubLanguage.forString("fr"));
        TestFactories.relations().newRelation(word1.getTopicId(), word2.getTopicId());
        TestFactories.relations().newRelation(word1.getTopicId(), word3.getTopicId());
        TestFactories.relations().newRelation(word1.getTopicId(), word4.getTopicId());

        final Context context = new Context(word1.getValue(), word1.getLanguage());

        //assertThat(context.getValues().size()).isEqualTo(2);
    }
}
