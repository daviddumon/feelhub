package com.feelhub.domain.feeling;

import com.feelhub.domain.feeling.*;
import com.feelhub.domain.related.Related;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsFeelingRelationBinder {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        feelingRelationBinder = injector.getInstance(FeelingRelationBinder.class);
    }

    @Test
    public void canCreateRelationsForAFeeling() {
        final Feeling feeling = TestFactories.feelings().newFeelingWithoutSentiments();
        final Sentiment firstSentiment = TestFactories.sentiments().newSentiment();
        final Sentiment secondSentiment = TestFactories.sentiments().newSentiment();
        final Sentiment thirdSentiment = TestFactories.sentiments().newSentiment();
        feeling.addSentiment(firstSentiment);
        feeling.addSentiment(secondSentiment);
        feeling.addSentiment(thirdSentiment);

        feelingRelationBinder.bind(feeling);

        final List<Related> relateds = Repositories.related().getAll();
        assertThat(relateds.size(), is(6));
        assertThat(relateds.get(0).getWeight(), is(1.0));
        assertThat(relateds.get(1).getWeight(), is(1.0));
        assertThat(relateds.get(2).getWeight(), is(1.0));
        assertThat(relateds.get(3).getWeight(), is(1.0));
        assertThat(relateds.get(4).getWeight(), is(1.0));
        assertThat(relateds.get(5).getWeight(), is(1.0));
    }

    private FeelingRelationBinder feelingRelationBinder;
}