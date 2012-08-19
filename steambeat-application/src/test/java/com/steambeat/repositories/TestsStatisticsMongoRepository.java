package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.statistics.*;
import com.steambeat.test.*;
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
        final Reference reference = TestFactories.references().newReference();
        final Statistics stat = new Statistics(reference, Granularity.day, date);
        stat.incrementJudgmentCount(new Judgment(reference, Feeling.good));
        stat.incrementJudgmentCount(new Judgment(reference, Feeling.bad));
        stat.incrementJudgmentCount(new Judgment(reference, Feeling.neutral));

        Repositories.statistics().add(stat);

        final DBCollection collection = mongo.getCollection("statistics");
        final DBObject query = new BasicDBObject();
        query.put("_id", stat.getId());
        final DBObject documentFound = collection.findOne(query);
        assertThat(documentFound, notNullValue());
        assertThat(documentFound.get("_id"), notNullValue());
        assertThat(documentFound.get("date"), is((Object) date.getMillis()));
        assertThat(documentFound.get("referenceId"), is((Object) reference.getId()));
        assertThat(documentFound.get("granularity"), is((Object) Granularity.day.toString()));
        assertThat(documentFound.get("good"), is((Object) 1));
        assertThat(documentFound.get("bad"), is((Object) 1));
        assertThat(documentFound.get("neutral"), is((Object) 1));
    }

    @Test
    public void canGetBySubjectGranularityAndInterval() {
        final Reference reference = TestFactories.references().newReference();
        Repositories.statistics().add(new Statistics(reference, Granularity.hour, new DateTime()));
        time.waitDays(1);
        Repositories.statistics().add(new Statistics(reference, Granularity.hour, new DateTime()));
        Repositories.statistics().add(new Statistics(TestFactories.references().newReference(), Granularity.day, new DateTime()));

        final List<Statistics> statistics = Repositories.statistics().forReference(reference, Granularity.hour, new Interval(new DateTime().minusDays(1), new DateTime()));

        assertThat(statistics.size(), is(1));
    }

    @Test
    public void canGetBySubjectGranularityReferenceAndOffset() {
        final Reference reference = TestFactories.references().newReference();
        final Statistics one = new Statistics(reference, Granularity.hour, new DateTime());
        one.incrementJudgmentCount(new Judgment(reference, Feeling.good));
        Repositories.statistics().add(one);
        time.waitHours(2);
        final Statistics two = new Statistics(reference, Granularity.hour, new DateTime());
        two.incrementJudgmentCount(new Judgment(reference, Feeling.good));
        Repositories.statistics().add(two);

        final List<Statistics> statistics = Repositories.statistics().forReference(reference, Granularity.hour, Granularity.hour.intervalFor(one.getDate(), two.getDate()));

        assertThat(statistics.size(), is(2));
        assertThat(statistics.get(0).getGood(), is(1));
        assertThat(statistics.get(1).getGood(), is(1));
    }

    @Test
    public void canGetWithGranularityDay() {
        final Reference reference = TestFactories.references().newReference();
        final Statistics one = new Statistics(reference, Granularity.day, new DateTime());
        one.incrementJudgmentCount(new Judgment(reference, Feeling.good));
        Repositories.statistics().add(one);
        time.waitDays(4);

        final List<Statistics> statistics = Repositories.statistics().forReference(reference, Granularity.day, Granularity.day.intervalFor(one.getDate(), one.getDate()));

        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGood(), is(1));
        assertThat(statistics.get(0).getBad(), is(0));
    }

    @Test
    public void canGetWithGranularityMonth() {
        final Reference reference = TestFactories.references().newReference();
        final Statistics one = new Statistics(reference, Granularity.month, new DateTime());
        one.incrementJudgmentCount(new Judgment(reference, Feeling.good));
        Repositories.statistics().add(one);
        time.waitMonths(4);

        final List<Statistics> statistics = Repositories.statistics().forReference(reference, Granularity.month, Granularity.month.intervalFor(one.getDate(), one.getDate()));

        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGood(), is(1));
        assertThat(statistics.get(0).getBad(), is(0));
    }

    @Test
    public void canGetWithGranularityYear() {
        final Reference reference = TestFactories.references().newReference();
        final Statistics one = new Statistics(reference, Granularity.year, new DateTime());
        one.incrementJudgmentCount(new Judgment(reference, Feeling.good));
        Repositories.statistics().add(one);
        time.waitYears(4);

        final List<Statistics> statistics = Repositories.statistics().forReference(reference, Granularity.year, Granularity.year.intervalFor(one.getDate(), one.getDate()));

        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGood(), is(1));
        assertThat(statistics.get(0).getBad(), is(0));
    }

    @Test
    public void canGetAllStatisticsForAReference() {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.statistics().newStatistics(reference, Granularity.day);
        TestFactories.statistics().newStatistics(reference, Granularity.all);
        TestFactories.statistics().newStatistics(reference, Granularity.hour);
        TestFactories.statistics().newStatistics(reference, Granularity.month);
        TestFactories.statistics().newStatistics(reference, Granularity.year);

        final List<Statistics> statistics = Repositories.statistics().forReferenceId(reference.getId());

        assertThat(statistics.size(), is(5));
    }
}
