package com.feelhub.web.search;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.feeling.Feeling;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.TestWithMongoRepository;
import com.feelhub.test.*;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsFeelingSearch extends TestWithMongoRepository {

    @Rule
    public WithDomainEvent event = new WithDomainEvent();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        feelingSearch = new FeelingSearch(getProvider());
    }

    @Test
    public void canGetOneFeeling() {
        TestFactories.feelings().newFeeling();

        final List<Feeling> feelings = feelingSearch.execute();

        assertThat(feelings.size(), is(1));
    }

    @Test
    public void canGetAllFeelings() {
        TestFactories.feelings().newFeelings(20);

        final List<Feeling> feelings = feelingSearch.execute();

        assertThat(feelings.size(), is(20));
    }

    @Test
    public void canSkip() {
        TestFactories.feelings().newFeelings(20);

        feelingSearch.withSkip(10);

        final List<Feeling> feelings = feelingSearch.execute();
        assertThat(feelings.size(), is(10));
    }

    @Test
    public void canLimit() {
        TestFactories.feelings().newFeelings(20);

        feelingSearch.withLimit(5);

        final List<Feeling> feelings = feelingSearch.execute();
        assertThat(feelings.size(), is(5));
    }

    @Test
    public void canLimitAndSkip() {
        TestFactories.feelings().newFeelings(30);

        feelingSearch.withLimit(5).withSkip(10);

        final List<Feeling> feelings = feelingSearch.execute();
        assertThat(feelings.size(), is(5));
        assertThat(feelings.get(0).getText(), is("i10"));
        assertThat(feelings.get(1).getText(), is("i11"));
        assertThat(feelings.get(2).getText(), is("i12"));
        assertThat(feelings.get(3).getText(), is("i13"));
        assertThat(feelings.get(4).getText(), is("i14"));
    }

    @Ignore
    @Test
    public void canGetFeelingsForTopic() {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.feelings().newFeelings(topic, 10);
        TestFactories.feelings().newFeelings(20);

        feelingSearch.withTopic(topic);

        final List<Feeling> feelings = feelingSearch.execute();
        assertThat(feelings.size(), is(10));
    }

    @Test
    public void alwaysIgnoreEmptyFeelings() {
        TestFactories.feelings().newFeelings(10);
        TestFactories.feelings().newFeelingWithText("");

        final List<Feeling> feelings = feelingSearch.execute();
        assertThat(feelings.size(), is(10));
    }

    private FeelingSearch feelingSearch;
}
