package com.feelhub.domain.feeling;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.reference.*;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.common.collect.Lists;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsFeelingManager {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        feelingManager = new FeelingManager();
    }

    @Test
    public void canChangeSentimentsReferencesForAConcept() {
        final Reference ref1 = TestFactories.references().newReference();
        final Reference ref2 = TestFactories.references().newReference();
        final Feeling op1 = TestFactories.feelings().newFeelingWithoutSentiments();
        final Feeling op2 = TestFactories.feelings().newFeelingWithoutSentiments();
        op1.addSentiment(ref1, SentimentValue.good);
        op2.addSentiment(ref1, SentimentValue.good);
        op1.addSentiment(ref2, SentimentValue.bad);
        op2.addSentiment(ref2, SentimentValue.bad);
        final ReferencePatch referencePatch = new ReferencePatch(ref1.getId());
        referencePatch.addOldReferenceId(ref2.getId());

        feelingManager.merge(referencePatch);

        final List<Sentiment> sentiments = Lists.newArrayList();
        sentiments.addAll(op1.getSentiments());
        sentiments.addAll(op2.getSentiments());
        for (final Sentiment sentiment : sentiments) {
            assertThat(sentiment.getReferenceId(), is(ref1.getId()));
        }
    }

    private FeelingManager feelingManager;
}
