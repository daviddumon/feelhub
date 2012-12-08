package com.feelhub.web.search;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.feeling.Feeling;
import com.feelhub.domain.topic.usable.real.RealTopic;
import com.feelhub.repositories.TestWithMongoRepository;
import com.feelhub.test.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

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

        assertThat(feelings.size()).isEqualTo(1);
    }

    @Test
    public void canGetAllFeelings() {
        TestFactories.feelings().newFeelings(20);

        final List<Feeling> feelings = feelingSearch.execute();

        assertThat(feelings.size()).isEqualTo(20);
    }

    @Test
    public void canSkip() {
        TestFactories.feelings().newFeelings(20);

        final List<Feeling> feelings = feelingSearch.withSkip(10).execute();

        assertThat(feelings.size()).isEqualTo(10);
    }

    @Test
    public void canLimit() {
        TestFactories.feelings().newFeelings(20);

        final List<Feeling> feelings = feelingSearch.withLimit(5).execute();

        assertThat(feelings.size()).isEqualTo(5);
    }

    @Test
    public void canLimitAndSkip() {
        TestFactories.feelings().newFeelings(30);

        final List<Feeling> feelings = feelingSearch.withLimit(5).withSkip(10).execute();

        assertThat(feelings.size()).isEqualTo(5);
        assertThat(feelings.get(0).getText()).isEqualTo("i10");
        assertThat(feelings.get(1).getText()).isEqualTo("i11");
        assertThat(feelings.get(2).getText()).isEqualTo("i12");
        assertThat(feelings.get(3).getText()).isEqualTo("i13");
        assertThat(feelings.get(4).getText()).isEqualTo("i14");
    }

    @Ignore
    @Test
    public void canGetFeelingsForTopic() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.feelings().newFeelings(realTopic.getId(), 10);
        TestFactories.feelings().newFeelings(20);

        final List<Feeling> feelings = feelingSearch.withTopicId(realTopic.getId()).execute();

        assertThat(feelings.size()).isEqualTo(10);
    }

    @Test
    public void alwaysIgnoreEmptyFeelings() {
        TestFactories.feelings().newFeelings(10);
        TestFactories.feelings().newFeelingWithText("");

        final List<Feeling> feelings = feelingSearch.execute();

        assertThat(feelings.size()).isEqualTo(10);
    }

    private FeelingSearch feelingSearch;
}
