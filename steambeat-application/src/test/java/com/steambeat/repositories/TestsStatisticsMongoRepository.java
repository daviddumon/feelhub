package com.steambeat.repositories;

import com.steambeat.domain.opinion.Feeling;
import com.steambeat.domain.subject.feed.Feed;
import com.steambeat.test.SystemTime;
import com.steambeat.test.testFactories.TestFactories;
import com.mongodb.*;
import com.steambeat.domain.statistics.*;
import org.joda.time.*;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsStatisticsMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    //@Rule
    //public WithDomainEvent domainEvent = new WithDomainEvent();

    @Test
    public void canPersist() {
        final DateTime date = new DateTime().plusDays(1);
        final Feed feed = TestFactories.feeds().newFeed();
        Statistics stat = new Statistics(feed, Granularity.day, date);
        stat.incrementOpinionCount(feed.createOpinion("myopinion", Feeling.good));
        stat.incrementOpinionCount(feed.createOpinion("myopinion", Feeling.bad));
        stat.incrementOpinionCount(feed.createOpinion("myopinion", Feeling.neutral));

        Repositories.statistics().add(stat);

        final DBCollection collection = mongo.getCollection("statistics");
        final DBObject query = new BasicDBObject();
        query.put("_id", stat.getId());
        final DBObject documentFound = collection.findOne(query);
        assertThat(documentFound, notNullValue());
        assertThat(documentFound.get("_id"), notNullValue());
        assertThat(documentFound.get("date"), is((Object) date.getMillis()));
        assertThat(documentFound.get("subjectId"), is((Object) feed.getUri()));
        assertThat(documentFound.get("granularity"), is((Object) Granularity.day.toString()));
        assertThat(documentFound.get("goodOpinions"), is((Object) 1));
        assertThat(documentFound.get("badOpinions"), is((Object) 1));
        assertThat(documentFound.get("neutralOpinions"), is((Object) 1));
    }

    @Test
    public void canGetByFeedGranularityAndInterval() {
        final Feed feed = TestFactories.feeds().newFeed("uri qui poutre");
        Repositories.statistics().add(new Statistics(feed, Granularity.hour, new DateTime()));
        time.waitDays(1);
        Repositories.statistics().add(new Statistics(feed, Granularity.hour, new DateTime()));
        Repositories.statistics().add(new Statistics(TestFactories.feeds().newFeed(), Granularity.day, new DateTime()));

        final List<Statistics> statistics = Repositories.statistics().forFeed(feed, Granularity.hour, new Interval(new DateTime().minusDays(1), new DateTime()));

        assertThat(statistics.size(), is(1));
    }

    @Test
    public void canGetByFeedGranularityReferenceAndOffset() {
        final Feed feed = TestFactories.feeds().newFeed("uri qui poutre");
        final Statistics one = new Statistics(feed, Granularity.hour, new DateTime());
        one.incrementOpinionCount(feed.createOpinion("myopinion", Feeling.good));
        Repositories.statistics().add(one);
        time.waitHours(2);
        final Statistics two = new Statistics(feed, Granularity.hour, new DateTime());
        two.incrementOpinionCount(feed.createOpinion("myopinion", Feeling.good));
        Repositories.statistics().add(two);

        final List<Statistics> statistics = Repositories.statistics().forFeed(feed, Granularity.hour, Granularity.hour.intervalFor(one.getDate(), two.getDate()));

        assertThat(statistics.size(), is(2));
        assertThat(statistics.get(0).getGoodOpinions(), is(1));
        assertThat(statistics.get(1).getGoodOpinions(), is(1));
    }

    @Test
    public void canGetWithGranularityDay() {
        final Feed feed = TestFactories.feeds().newFeed("uri qui poutre");
        final Statistics one = new Statistics(feed, Granularity.day, new DateTime());
        one.incrementOpinionCount(feed.createOpinion("myopinion", Feeling.good));
        Repositories.statistics().add(one);
        time.waitDays(4);

        final List<Statistics> statistics = Repositories.statistics().forFeed(feed, Granularity.day, Granularity.day.intervalFor(one.getDate(), one.getDate()));

        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGoodOpinions(), is(1));
        assertThat(statistics.get(0).getBadOpinions(), is(0));
        assertThat(statistics.get(0).getNeutralOpinions(), is(0));
    }

    @Test
    public void canGetWithGranularityMonth() {
        final Feed feed = TestFactories.feeds().newFeed("uri qui poutre");
        final Statistics one = new Statistics(feed, Granularity.month, new DateTime());
        one.incrementOpinionCount(feed.createOpinion("myopinion", Feeling.good));
        Repositories.statistics().add(one);
        time.waitMonths(4);

        final List<Statistics> statistics = Repositories.statistics().forFeed(feed, Granularity.month, Granularity.month.intervalFor(one.getDate(), one.getDate()));

        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGoodOpinions(), is(1));
        assertThat(statistics.get(0).getBadOpinions(), is(0));
        assertThat(statistics.get(0).getNeutralOpinions(), is(0));
    }

    @Test
    public void canGetWithGranularityYear() {
        final Feed feed = TestFactories.feeds().newFeed("uri qui poutre");
        final Statistics one = new Statistics(feed, Granularity.year, new DateTime());
        one.incrementOpinionCount(feed.createOpinion("myopinion", Feeling.good));
        Repositories.statistics().add(one);
        time.waitYears(4);

        final List<Statistics> statistics = Repositories.statistics().forFeed(feed, Granularity.year, Granularity.year.intervalFor(one.getDate(), one.getDate()));

        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGoodOpinions(), is(1));
        assertThat(statistics.get(0).getBadOpinions(), is(0));
        assertThat(statistics.get(0).getNeutralOpinions(), is(0));
    }
}
