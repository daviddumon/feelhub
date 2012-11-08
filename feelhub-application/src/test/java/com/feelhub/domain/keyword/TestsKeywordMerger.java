package com.feelhub.domain.keyword;

import com.feelhub.domain.feeling.*;
import com.feelhub.domain.illustration.Illustration;
import com.feelhub.domain.reference.Reference;
import com.feelhub.domain.relation.Relation;
import com.feelhub.domain.statistics.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.google.common.collect.Lists;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsKeywordMerger {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        keywordMerger = new KeywordMerger();
    }

    @Test
    public void mergeKeywords() {
        final Reference good = TestFactories.references().newReference();
        final Reference bad = TestFactories.references().newReference();

        keywordMerger.merge(createListOfKeyword(good, bad));

        for (final Keyword keyword : Repositories.keywords().getAll()) {
            assertThat(keyword.getReferenceId(), is(good.getId()));
        }
    }

    @Test
    public void mergeReferences() {
        final Reference good = TestFactories.references().newReference();
        final Reference bad = TestFactories.references().newReference();

        keywordMerger.merge(createListOfKeyword(good, bad));

        final List<Reference> references = Repositories.references().getAll();
        assertThat(references.get(0).isActive(), is(true));
        assertThat(references.get(1).isActive(), is(false));
        assertThat(references.get(2).isActive(), is(false));
    }

    @Test
    public void mergeIllustrations() {
        final Reference good = TestFactories.references().newReference();
        final Reference bad = TestFactories.references().newReference();
        TestFactories.illustrations().newIllustration(good);
        TestFactories.illustrations().newIllustration(bad);

        keywordMerger.merge(createListOfKeyword(good, bad));

        for (final Illustration illustration : Repositories.illustrations().getAll()) {
            assertThat(illustration.getReferenceId(), is(good.getId()));
        }
    }

    @Test
    public void mergeFeelings() {
        final Reference good = TestFactories.references().newReference();
        final Reference bad = TestFactories.references().newReference();
        TestFactories.feelings().newFeelings(good, 10);
        TestFactories.feelings().newFeelings(bad, 10);

        keywordMerger.merge(createListOfKeyword(good, bad));

        for (final Feeling feeling : Repositories.feelings().getAll()) {
            for (final Sentiment sentiment : feeling.getSentiments()) {
                assertThat(sentiment.getReferenceId(), is(good.getId()));
            }
        }
    }

    @Test
    public void mergeRelations() {
        final Reference good = TestFactories.references().newReference();
        final Reference bad = TestFactories.references().newReference();
        final Reference ref3 = TestFactories.references().newReference();
        final Relation relation1 = TestFactories.relations().newRelation(bad, ref3);
        final Relation relation2 = TestFactories.relations().newRelation(ref3, bad);

        keywordMerger.merge(createListOfKeyword(good, bad));

        assertThat(relation1.getFromId(), is(good.getId()));
        assertThat(relation1.getToId(), is(ref3.getId()));
        assertThat(relation2.getFromId(), is(ref3.getId()));
        assertThat(relation2.getToId(), is(good.getId()));
    }

    @Test
    public void mergeStatistics() {
        final Reference good = TestFactories.references().newReference();
        final Reference bad = TestFactories.references().newReference();
        TestFactories.statistics().newStatisticsWithSentiments(bad, Granularity.hour);

        keywordMerger.merge(createListOfKeyword(good, bad));

        final Statistics statistics = Repositories.statistics().getAll().get(0);
        assertThat(statistics.getReferenceId(), is(good.getId()));
    }

    private List<Keyword> createListOfKeyword(final Reference good, final Reference bad) {
        final List<Keyword> keywords = Lists.newArrayList();
        final Keyword first = TestFactories.keywords().newKeyword("first", FeelhubLanguage.reference(), good);
        keywords.add(first);
        time.waitDays(1);
        keywords.add(TestFactories.keywords().newKeyword("second", FeelhubLanguage.none(), bad));
        keywords.add(TestFactories.keywords().newKeyword("third"));
        return keywords;
    }

    private KeywordMerger keywordMerger;
}
