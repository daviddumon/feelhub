package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.statistics.*;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.SystemTime;
import com.steambeat.test.testFactories.TestFactories;
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
        final Subject subject = TestFactories.subjects().newWebPage();
        final Statistics stat = new Statistics(subject, Granularity.day, date);
        stat.incrementJudgmentCount(new Judgment(subject, Feeling.good));
        stat.incrementJudgmentCount(new Judgment(subject, Feeling.bad));

        Repositories.statistics().add(stat);

        final DBCollection collection = mongo.getCollection("statistics");
        final DBObject query = new BasicDBObject();
        query.put("_id", stat.getId());
        final DBObject documentFound = collection.findOne(query);
        assertThat(documentFound, notNullValue());
        assertThat(documentFound.get("_id"), notNullValue());
        assertThat(documentFound.get("date"), is((Object) date.getMillis()));
        assertThat(documentFound.get("subjectId"), is((Object) subject.getId()));
        assertThat(documentFound.get("granularity"), is((Object) Granularity.day.toString()));
        assertThat(documentFound.get("goodJudgments"), is((Object) 1));
        assertThat(documentFound.get("badJudgments"), is((Object) 1));
    }

    @Test
    public void canGetBySubjectGranularityAndInterval() {
        final WebPage webPage = TestFactories.subjects().newWebPage();
        Repositories.statistics().add(new Statistics(webPage, Granularity.hour, new DateTime()));
        time.waitDays(1);
        Repositories.statistics().add(new Statistics(webPage, Granularity.hour, new DateTime()));
        Repositories.statistics().add(new Statistics(TestFactories.subjects().newWebPage(), Granularity.day, new DateTime()));

        final List<Statistics> statistics = Repositories.statistics().forSubject(webPage, Granularity.hour, new Interval(new DateTime().minusDays(1), new DateTime()));

        assertThat(statistics.size(), is(1));
    }

    @Test
    public void canGetBySubjectGranularityReferenceAndOffset() {
        final WebPage webPage = TestFactories.subjects().newWebPage();
        final Statistics one = new Statistics(webPage, Granularity.hour, new DateTime());
        one.incrementJudgmentCount(new Judgment(webPage, Feeling.good));
        Repositories.statistics().add(one);
        time.waitHours(2);
        final Statistics two = new Statistics(webPage, Granularity.hour, new DateTime());
        two.incrementJudgmentCount(new Judgment(webPage, Feeling.good));
        Repositories.statistics().add(two);

        final List<Statistics> statistics = Repositories.statistics().forSubject(webPage, Granularity.hour, Granularity.hour.intervalFor(one.getDate(), two.getDate()));

        assertThat(statistics.size(), is(2));
        assertThat(statistics.get(0).getGoodJudgments(), is(1));
        assertThat(statistics.get(1).getGoodJudgments(), is(1));
    }

    @Test
    public void canGetWithGranularityDay() {
        final WebPage webPage = TestFactories.subjects().newWebPage();
        final Statistics one = new Statistics(webPage, Granularity.day, new DateTime());
        one.incrementJudgmentCount(new Judgment(webPage, Feeling.good));
        Repositories.statistics().add(one);
        time.waitDays(4);

        final List<Statistics> statistics = Repositories.statistics().forSubject(webPage, Granularity.day, Granularity.day.intervalFor(one.getDate(), one.getDate()));

        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGoodJudgments(), is(1));
        assertThat(statistics.get(0).getBadJudgments(), is(0));
    }

    @Test
    public void canGetWithGranularityMonth() {
        final WebPage webPage = TestFactories.subjects().newWebPage();
        final Statistics one = new Statistics(webPage, Granularity.month, new DateTime());
        one.incrementJudgmentCount(new Judgment(webPage, Feeling.good));
        Repositories.statistics().add(one);
        time.waitMonths(4);

        final List<Statistics> statistics = Repositories.statistics().forSubject(webPage, Granularity.month, Granularity.month.intervalFor(one.getDate(), one.getDate()));

        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGoodJudgments(), is(1));
        assertThat(statistics.get(0).getBadJudgments(), is(0));
    }

    @Test
    public void canGetWithGranularityYear() {
        final WebPage webPage = TestFactories.subjects().newWebPage();
        final Statistics one = new Statistics(webPage, Granularity.year, new DateTime());
        one.incrementJudgmentCount(new Judgment(webPage, Feeling.good));
        Repositories.statistics().add(one);
        time.waitYears(4);

        final List<Statistics> statistics = Repositories.statistics().forSubject(webPage, Granularity.year, Granularity.year.intervalFor(one.getDate(), one.getDate()));

        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGoodJudgments(), is(1));
        assertThat(statistics.get(0).getBadJudgments(), is(0));
    }
}
