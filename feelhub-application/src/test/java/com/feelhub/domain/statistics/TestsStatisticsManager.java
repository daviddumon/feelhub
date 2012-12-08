package com.feelhub.domain.statistics;

import com.feelhub.domain.topic.TopicPatch;
import com.feelhub.domain.topic.usable.real.RealTopic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsStatisticsManager {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        statisticsManager = injector.getInstance(StatisticsManager.class);
    }

    @Test
    public void canChangeStatisticsTopics() {
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        TestFactories.statistics().newStatisticsWithSentiments(realTopic1.getId(), Granularity.all);
        TestFactories.statistics().newStatisticsWithSentiments(realTopic1.getId(), Granularity.day);
        TestFactories.statistics().newStatisticsWithSentiments(realTopic1.getId(), Granularity.hour);
        TestFactories.statistics().newStatisticsWithSentiments(realTopic2.getId(), Granularity.hour);
        TestFactories.statistics().newStatisticsWithSentiments(realTopic2.getId(), Granularity.month);
        final TopicPatch topicPatch = new TopicPatch(realTopic1.getId());
        topicPatch.addOldTopicId(realTopic2.getId());

        statisticsManager.merge(topicPatch);

        assertThat(Repositories.statistics().getAll().size(), is(4));
        final List<Statistics> all = Repositories.statistics().forTopicId(realTopic1.getId(), Granularity.all);
        assertThat(all.size(), is(1));
        assertThat(all.get(0).getGood(), is(3));
        assertThat(all.get(0).getBad(), is(2));
        assertThat(all.get(0).getNeutral(), is(1));
        final List<Statistics> day = Repositories.statistics().forTopicId(realTopic1.getId(), Granularity.day);
        assertThat(day.size(), is(1));
        assertThat(day.get(0).getGood(), is(3));
        assertThat(day.get(0).getBad(), is(2));
        assertThat(day.get(0).getNeutral(), is(1));
        final List<Statistics> hour = Repositories.statistics().forTopicId(realTopic1.getId(), Granularity.hour);
        assertThat(hour.size(), is(1));
        assertThat(hour.get(0).getGood(), is(6));
        assertThat(hour.get(0).getBad(), is(4));
        assertThat(hour.get(0).getNeutral(), is(2));
        final List<Statistics> month = Repositories.statistics().forTopicId(realTopic1.getId(), Granularity.month);
        assertThat(month.size(), is(1));
        assertThat(month.get(0).getGood(), is(3));
        assertThat(month.get(0).getBad(), is(2));
        assertThat(month.get(0).getNeutral(), is(1));
    }

    private StatisticsManager statisticsManager;
}
