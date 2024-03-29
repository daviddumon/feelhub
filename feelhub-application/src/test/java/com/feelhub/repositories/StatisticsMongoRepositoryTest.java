package com.feelhub.repositories;

import com.feelhub.domain.statistics.*;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.test.*;
import com.mongodb.*;
import org.joda.time.*;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class StatisticsMongoRepositoryTest extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canPersist() {
        final DateTime date = new DateTime().plusDays(1);
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Statistics stat = new Statistics(realTopic.getId(), Granularity.day, date);
        stat.incrementFeelingCount(TestFactories.feelings().happyFeeling(realTopic));
        stat.incrementFeelingCount(TestFactories.feelings().sadFeeling(realTopic));
        stat.incrementFeelingCount(TestFactories.feelings().boredFeeling(realTopic));

        Repositories.statistics().add(stat);

        final DBCollection collection = getMongo().getCollection("statistics");
        final DBObject query = new BasicDBObject();
        query.put("_id", stat.getId());
        final DBObject documentFound = collection.findOne(query);
        assertThat(documentFound, notNullValue());
        assertThat(documentFound.get("_id"), notNullValue());
        assertThat(documentFound.get("date"), is((Object) date.getMillis()));
        assertThat(documentFound.get("topicId"), is((Object) realTopic.getId()));
        assertThat(documentFound.get("granularity"), is((Object) Granularity.day.toString()));
        assertThat(documentFound.get("happy"), is((Object) 1));
        assertThat(documentFound.get("sad"), is((Object) 1));
        assertThat(documentFound.get("bored"), is((Object) 1));
    }

    @Test
    public void canGetByTopicGranularityAndInterval() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        Repositories.statistics().add(new Statistics(realTopic.getId(), Granularity.hour, new DateTime()));
        time.waitDays(1);
        Repositories.statistics().add(new Statistics(realTopic.getId(), Granularity.hour, new DateTime()));
        Repositories.statistics().add(new Statistics(TestFactories.topics().newCompleteRealTopic().getId(), Granularity.day, new DateTime()));

        final List<Statistics> statistics = Repositories.statistics().forTopicId(realTopic.getId(), Granularity.hour, new Interval(new DateTime().minusDays(1), new DateTime()));

        assertThat(statistics.size(), is(1));
    }

    @Test
    public void canGetByTopicGranularityTopicAndOffset() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Statistics one = new Statistics(realTopic.getId(), Granularity.hour, new DateTime());
        one.incrementFeelingCount(TestFactories.feelings().happyFeeling(realTopic));
        Repositories.statistics().add(one);
        time.waitHours(2);
        final Statistics two = new Statistics(realTopic.getId(), Granularity.hour, new DateTime());
        two.incrementFeelingCount(TestFactories.feelings().sadFeeling(realTopic));
        Repositories.statistics().add(two);

        final List<Statistics> statistics = Repositories.statistics().forTopicId(realTopic.getId(), Granularity.hour, Granularity.hour.intervalFor(one.getDate(), two.getDate()));

        assertThat(statistics.size(), is(2));
        assertThat(statistics.get(0).getHappy(), is(1));
        assertThat(statistics.get(1).getSad(), is(1));
    }

    @Test
    public void canGetWithGranularityDay() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Statistics one = new Statistics(realTopic.getId(), Granularity.day, new DateTime());
        one.incrementFeelingCount(TestFactories.feelings().happyFeeling(realTopic));
        Repositories.statistics().add(one);
        time.waitDays(4);

        final List<Statistics> statistics = Repositories.statistics().forTopicId(realTopic.getId(), Granularity.day, Granularity.day.intervalFor(one.getDate(), one.getDate()));

        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getHappy(), is(1));
        assertThat(statistics.get(0).getSad(), is(0));
    }

    @Test
    public void canGetWithGranularityMonth() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Statistics one = new Statistics(realTopic.getId(), Granularity.month, new DateTime());
        one.incrementFeelingCount(TestFactories.feelings().happyFeeling(realTopic));
        Repositories.statistics().add(one);
        time.waitMonths(4);

        final List<Statistics> statistics = Repositories.statistics().forTopicId(realTopic.getId(), Granularity.month, Granularity.month.intervalFor(one.getDate(), one.getDate()));

        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getHappy(), is(1));
        assertThat(statistics.get(0).getSad(), is(0));
    }

    @Test
    public void canGetWithGranularityYear() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Statistics one = new Statistics(realTopic.getId(), Granularity.year, new DateTime());
        one.incrementFeelingCount(TestFactories.feelings().happyFeeling(realTopic));
        Repositories.statistics().add(one);
        time.waitYears(4);

        final List<Statistics> statistics = Repositories.statistics().forTopicId(realTopic.getId(), Granularity.year, Granularity.year.intervalFor(one.getDate(), one.getDate()));

        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getHappy(), is(1));
        assertThat(statistics.get(0).getSad(), is(0));
    }

    @Test
    public void canGetAllStatisticsForATopic() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.statistics().newStatistics(realTopic.getId(), Granularity.day);
        TestFactories.statistics().newStatistics(realTopic.getId(), Granularity.all);
        TestFactories.statistics().newStatistics(realTopic.getId(), Granularity.hour);
        TestFactories.statistics().newStatistics(realTopic.getId(), Granularity.month);
        TestFactories.statistics().newStatistics(realTopic.getId(), Granularity.year);

        final List<Statistics> statistics = Repositories.statistics().forTopicId(realTopic.getId());

        assertThat(statistics.size(), is(5));
    }
}
