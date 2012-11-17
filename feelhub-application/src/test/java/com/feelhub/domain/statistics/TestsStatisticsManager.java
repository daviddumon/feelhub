package com.feelhub.domain.statistics;

import com.feelhub.domain.topic.*;
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
        final Topic topic1 = TestFactories.topics().newTopic();
        final Topic topic2 = TestFactories.topics().newTopic();
        TestFactories.statistics().newStatisticsWithSentiments(topic1.getId(), Granularity.all);
        TestFactories.statistics().newStatisticsWithSentiments(topic1.getId(), Granularity.day);
        TestFactories.statistics().newStatisticsWithSentiments(topic1.getId(), Granularity.hour);
        TestFactories.statistics().newStatisticsWithSentiments(topic2.getId(), Granularity.hour);
        TestFactories.statistics().newStatisticsWithSentiments(topic2.getId(), Granularity.month);
        final TopicPatch topicPatch = new TopicPatch(topic1.getId());
        topicPatch.addOldTopicId(topic2.getId());

        statisticsManager.merge(topicPatch);

        assertThat(Repositories.statistics().getAll().size(), is(4));
        final List<Statistics> all = Repositories.statistics().forTopicId(topic1.getId(), Granularity.all);
        assertThat(all.size(), is(1));
        assertThat(all.get(0).getGood(), is(3));
        assertThat(all.get(0).getBad(), is(2));
        assertThat(all.get(0).getNeutral(), is(1));
        final List<Statistics> day = Repositories.statistics().forTopicId(topic1.getId(), Granularity.day);
        assertThat(day.size(), is(1));
        assertThat(day.get(0).getGood(), is(3));
        assertThat(day.get(0).getBad(), is(2));
        assertThat(day.get(0).getNeutral(), is(1));
        final List<Statistics> hour = Repositories.statistics().forTopicId(topic1.getId(), Granularity.hour);
        assertThat(hour.size(), is(1));
        assertThat(hour.get(0).getGood(), is(6));
        assertThat(hour.get(0).getBad(), is(4));
        assertThat(hour.get(0).getNeutral(), is(2));
        final List<Statistics> month = Repositories.statistics().forTopicId(topic1.getId(), Granularity.month);
        assertThat(month.size(), is(1));
        assertThat(month.get(0).getGood(), is(3));
        assertThat(month.get(0).getBad(), is(2));
        assertThat(month.get(0).getNeutral(), is(1));
    }

    private StatisticsManager statisticsManager;
}
