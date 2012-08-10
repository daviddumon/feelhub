package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.statistics.*;
import com.steambeat.domain.topic.Topic;
import com.steambeat.test.SystemTime;
import com.steambeat.test.TestFactories;
import org.joda.time.*;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsStatisticsMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canPersist() {
        final DateTime date = new DateTime().plusDays(1);
        final Topic topic = TestFactories.topics().newTopic();
        final Statistics stat = new Statistics(topic, Granularity.day, date);
        stat.incrementJudgmentCount(new Judgment(topic, Feeling.good));
        stat.incrementJudgmentCount(new Judgment(topic, Feeling.bad));
        stat.incrementJudgmentCount(new Judgment(topic, Feeling.neutral));

        Repositories.statistics().add(stat);

        final DBCollection collection = mongo.getCollection("statistics");
        final DBObject query = new BasicDBObject();
        query.put("_id", stat.getId());
        final DBObject documentFound = collection.findOne(query);
        assertThat(documentFound, notNullValue());
        assertThat(documentFound.get("_id"), notNullValue());
        assertThat(documentFound.get("date"), is((Object) date.getMillis()));
        assertThat(documentFound.get("topicId"), is((Object) topic.getId()));
        assertThat(documentFound.get("granularity"), is((Object) Granularity.day.toString()));
        assertThat(documentFound.get("good"), is((Object) 1));
        assertThat(documentFound.get("bad"), is((Object) 1));
        assertThat(documentFound.get("neutral"), is((Object) 1));
    }

    @Test
    public void canGetBySubjectGranularityAndInterval() {
        final Topic topic = TestFactories.topics().newTopic();
        Repositories.statistics().add(new Statistics(topic, Granularity.hour, new DateTime()));
        time.waitDays(1);
        Repositories.statistics().add(new Statistics(topic, Granularity.hour, new DateTime()));
        Repositories.statistics().add(new Statistics(TestFactories.topics().newTopic(), Granularity.day, new DateTime()));

        final List<Statistics> statistics = Repositories.statistics().forTopic(topic, Granularity.hour, new Interval(new DateTime().minusDays(1), new DateTime()));

        assertThat(statistics.size(), is(1));
    }

    @Test
    public void canGetBySubjectGranularityReferenceAndOffset() {
        final Topic topic = TestFactories.topics().newTopic();
        final Statistics one = new Statistics(topic, Granularity.hour, new DateTime());
        one.incrementJudgmentCount(new Judgment(topic, Feeling.good));
        Repositories.statistics().add(one);
        time.waitHours(2);
        final Statistics two = new Statistics(topic, Granularity.hour, new DateTime());
        two.incrementJudgmentCount(new Judgment(topic, Feeling.good));
        Repositories.statistics().add(two);

        final List<Statistics> statistics = Repositories.statistics().forTopic(topic, Granularity.hour, Granularity.hour.intervalFor(one.getDate(), two.getDate()));

        assertThat(statistics.size(), is(2));
        assertThat(statistics.get(0).getGood(), is(1));
        assertThat(statistics.get(1).getGood(), is(1));
    }

    @Test
    public void canGetWithGranularityDay() {
        final Topic topic = TestFactories.topics().newTopic();
        final Statistics one = new Statistics(topic, Granularity.day, new DateTime());
        one.incrementJudgmentCount(new Judgment(topic, Feeling.good));
        Repositories.statistics().add(one);
        time.waitDays(4);

        final List<Statistics> statistics = Repositories.statistics().forTopic(topic, Granularity.day, Granularity.day.intervalFor(one.getDate(), one.getDate()));

        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGood(), is(1));
        assertThat(statistics.get(0).getBad(), is(0));
    }

    @Test
    public void canGetWithGranularityMonth() {
        final Topic topic = TestFactories.topics().newTopic();
        final Statistics one = new Statistics(topic, Granularity.month, new DateTime());
        one.incrementJudgmentCount(new Judgment(topic, Feeling.good));
        Repositories.statistics().add(one);
        time.waitMonths(4);

        final List<Statistics> statistics = Repositories.statistics().forTopic(topic, Granularity.month, Granularity.month.intervalFor(one.getDate(), one.getDate()));

        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGood(), is(1));
        assertThat(statistics.get(0).getBad(), is(0));
    }

    @Test
    public void canGetWithGranularityYear() {
        final Topic topic = TestFactories.topics().newTopic();
        final Statistics one = new Statistics(topic, Granularity.year, new DateTime());
        one.incrementJudgmentCount(new Judgment(topic, Feeling.good));
        Repositories.statistics().add(one);
        time.waitYears(4);

        final List<Statistics> statistics = Repositories.statistics().forTopic(topic, Granularity.year, Granularity.year.intervalFor(one.getDate(), one.getDate()));

        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGood(), is(1));
        assertThat(statistics.get(0).getBad(), is(0));
    }
}
