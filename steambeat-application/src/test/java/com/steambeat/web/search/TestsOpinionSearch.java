package com.steambeat.web.search;

import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.statistics.Granularity;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.feed.Feed;
import com.steambeat.repositories.TestWithMongoRepository;
import com.steambeat.test.SystemTime;
import com.steambeat.test.testFactories.TestFactories;
import org.joda.time.Interval;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpinionSearch extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        opinionSearch = new OpinionSearch(getProvider());
    }

    @Test
    public void canGetLastOpinions() {
        for (int i = 0; i < 51; i++) {
            TestFactories.opinions().newOpinion();
        }

        final List<Opinion> lastOpinions = opinionSearch.last();

        assertThat(lastOpinions.size(), is(50));
    }

    @Test
    public void canGetForAnHourInterval() {
        final Interval interval = Granularity.hour.intervalFor(time.getNow());
        final Opinion opinion1 = TestFactories.opinions().newOpinion();
        final Opinion opinion2 = TestFactories.opinions().newOpinion();
        time.waitHours(2);

        final List<Opinion> opinions = opinionSearch.forInterval(interval);

        assertThat(opinions, notNullValue());
        assertThat(opinions.size(), is(2));
        assertThat(opinions.get(0), is(opinion1));
        assertThat(opinions.get(1), is(opinion2));
    }

    @Test
    public void canGetForASubject() {
        final Feed feed = TestFactories.feeds().newFeed();
        final Feed feed2 = TestFactories.feeds().newFeed();
        Opinion opinion1 = addOpinionForSubject(feed);
        Opinion opinion2 = addOpinionForSubject(feed2);

        final List<Opinion> opinions = opinionSearch.forSubject(opinion1.getSubject());

        assertThat(opinions, notNullValue());
        assertThat(opinions.size(), is(1));
        assertThat(opinions.get(0), is(opinion1));
    }

    @Test
    public void canGetForSubjectIntervalSkipAndLimit() {
        //final Feed feed = TestFactories.feeds().newFeed();
        //Repositories.opinions().add(new Opinion("1", Feeling.good, feed));
        //time.waitHours(10);
        //final Opinion opinionStart = addOpinionForSubject(feed);
        //Repositories.opinions().add(new Opinion("2", Feeling.good, feed));
        ////final Opinion result0 = addOpinionForSubject(feed);
        ////final Opinion result1 = addOpinionForSubject(feed);
        ////final Opinion result2 = addOpinionForSubject(feed);
        //Repositories.opinions().add(new Opinion("3", Feeling.good, feed));
        //Repositories.opinions().add(new Opinion("4", Feeling.good, feed));
        //Repositories.opinions().add(new Opinion("5", Feeling.good, feed));
        //time.waitHours(10);
        //addOpinionForSubject(feed);
        //final Interval interval = Granularity.hour.intervalFor(opinionStart.getCreationDate());
        //
        //final List<Opinion> opinions = Repositories.opinions().forIntervalSubjectSkipAndLimit(interval, feed, 2, 3);
        //
        //assertThat(opinions, notNullValue());
        //assertThat(opinions.size(), is(3));
        ////assertThat(opinions.get(0), is(result0));
        ////assertThat(opinions.get(1), is(result1));
        ////assertThat(opinions.get(2), is(result2));
        //System.out.println(opinions.get(0).getText());
        //System.out.println(opinions.get(1).getText());
        //System.out.println(opinions.get(2).getText());
    }

    private Opinion addOpinionForSubject(final Subject subject) {
        return TestFactories.opinions().newOpinionForSubject(subject);
    }

    private OpinionSearch opinionSearch;
}
