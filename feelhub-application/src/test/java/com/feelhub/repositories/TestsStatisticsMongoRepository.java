package com.feelhub.repositories;

import com.feelhub.domain.feeling.*;
import com.feelhub.domain.statistics.*;
import com.feelhub.domain.topic.Topic;
import com.feelhub.test.*;
import com.mongodb.*;
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
        final Statistics stat = new Statistics(topic.getId(), Granularity.day, date);
        stat.incrementSentimentCount(new Sentiment(topic.getId(), SentimentValue.good));
        stat.incrementSentimentCount(new Sentiment(topic.getId(), SentimentValue.bad));
        stat.incrementSentimentCount(new Sentiment(topic.getId(), SentimentValue.neutral));

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
    public void canGetByTopicGranularityAndInterval() {
        final Topic topic = TestFactories.topics().newTopic();
        Repositories.statistics().add(new Statistics(topic.getId(), Granularity.hour, new DateTime()));
        time.waitDays(1);
        Repositories.statistics().add(new Statistics(topic.getId(), Granularity.hour, new DateTime()));
        Repositories.statistics().add(new Statistics(TestFactories.topics().newTopic().getId(), Granularity.day, new DateTime()));

        final List<Statistics> statistics = Repositories.statistics().forTopicId(topic.getId(), Granularity.hour, new Interval(new DateTime().minusDays(1), new DateTime()));

        assertThat(statistics.size(), is(1));
    }

    @Test
    public void canGetByTopicGranularityTopicAndOffset() {
        final Topic topic = TestFactories.topics().newTopic();
        final Statistics one = new Statistics(topic.getId(), Granularity.hour, new DateTime());
        one.incrementSentimentCount(new Sentiment(topic.getId(), SentimentValue.good));
        Repositories.statistics().add(one);
        time.waitHours(2);
        final Statistics two = new Statistics(topic.getId(), Granularity.hour, new DateTime());
        two.incrementSentimentCount(new Sentiment(topic.getId(), SentimentValue.good));
        Repositories.statistics().add(two);

        final List<Statistics> statistics = Repositories.statistics().forTopicId(topic.getId(), Granularity.hour, Granularity.hour.intervalFor(one.getDate(), two.getDate()));

        assertThat(statistics.size(), is(2));
        assertThat(statistics.get(0).getGood(), is(1));
        assertThat(statistics.get(1).getGood(), is(1));
    }

    @Test
    public void canGetWithGranularityDay() {
        final Topic topic = TestFactories.topics().newTopic();
        final Statistics one = new Statistics(topic.getId(), Granularity.day, new DateTime());
        one.incrementSentimentCount(new Sentiment(topic.getId(), SentimentValue.good));
        Repositories.statistics().add(one);
        time.waitDays(4);

        final List<Statistics> statistics = Repositories.statistics().forTopicId(topic.getId(), Granularity.day, Granularity.day.intervalFor(one.getDate(), one.getDate()));

        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGood(), is(1));
        assertThat(statistics.get(0).getBad(), is(0));
    }

    @Test
    public void canGetWithGranularityMonth() {
        final Topic topic = TestFactories.topics().newTopic();
        final Statistics one = new Statistics(topic.getId(), Granularity.month, new DateTime());
        one.incrementSentimentCount(new Sentiment(topic.getId(), SentimentValue.good));
        Repositories.statistics().add(one);
        time.waitMonths(4);

        final List<Statistics> statistics = Repositories.statistics().forTopicId(topic.getId(), Granularity.month, Granularity.month.intervalFor(one.getDate(), one.getDate()));

        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGood(), is(1));
        assertThat(statistics.get(0).getBad(), is(0));
    }

    @Test
    public void canGetWithGranularityYear() {
        final Topic topic = TestFactories.topics().newTopic();
        final Statistics one = new Statistics(topic.getId(), Granularity.year, new DateTime());
        one.incrementSentimentCount(new Sentiment(topic.getId(), SentimentValue.good));
        Repositories.statistics().add(one);
        time.waitYears(4);

        final List<Statistics> statistics = Repositories.statistics().forTopicId(topic.getId(), Granularity.year, Granularity.year.intervalFor(one.getDate(), one.getDate()));

        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGood(), is(1));
        assertThat(statistics.get(0).getBad(), is(0));
    }

    @Test
    public void canGetAllStatisticsForATopic() {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.statistics().newStatistics(topic, Granularity.day);
        TestFactories.statistics().newStatistics(topic, Granularity.all);
        TestFactories.statistics().newStatistics(topic, Granularity.hour);
        TestFactories.statistics().newStatistics(topic, Granularity.month);
        TestFactories.statistics().newStatistics(topic, Granularity.year);

        final List<Statistics> statistics = Repositories.statistics().forTopicId(topic.getId());

        assertThat(statistics.size(), is(5));
    }
}
