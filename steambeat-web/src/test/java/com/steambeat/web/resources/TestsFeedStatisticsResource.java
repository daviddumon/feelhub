package com.steambeat.web.resources;

import com.steambeat.domain.opinion.Feeling;
import com.steambeat.test.SystemTime;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.domain.statistics.*;
import com.steambeat.web.*;
import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.restlet.data.*;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsFeedStatisticsResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        uri = "http://test.com";
        statistics = TestFactories.statistics().newFeedStat(uri);
    }

    @Test
    public void canRepresent() {
        final ClientResource resource = restlet.newClientResource("/feeds/" + uri + "/stats:" + new DateTime().getMillis() + "." + new DateTime().getMillis() + ";hour");

        final Representation representation = resource.get();

        assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
        assertThat(representation.getMediaType(), is(MediaType.APPLICATION_JSON));
    }

    @Test
    public void canFetchSingleHour() throws JSONException {
        statistics.incrementOpinionCount(Feeling.neutral);
        statistics.incrementOpinionCount(Feeling.neutral);
        statistics.incrementOpinionCount(Feeling.good);
        final ClientResource resource = restlet.newClientResource("/feeds/" + uri + "/stats:" + new DateTime().minus(1).getMillis() + "." + new DateTime().plus(1).getMillis() + ";hour");
        time.waitMinutes(60);
        TestFactories.statistics().newFeedStat(uri);
        time.waitDays(1);

        final JsonRepresentation representation = (JsonRepresentation) resource.get();

        final JSONObject json = representation.getJsonObject();
        assertThat(json.getString("granularity"), Matchers.is(Granularity.hour.toString()));
        final JSONArray stats = json.getJSONArray("stats");
        assertThat(stats.length(), is(1));
        assertThat(stats.getJSONObject(0).getLong("time"), Matchers.is(statistics.getDate().getMillis()));
        final JSONObject opinions = stats.getJSONObject(0).getJSONObject("opinions");
        assertThat(opinions.getInt("good"), is(1));
        assertThat(opinions.getInt("bad"), is(0));
        assertThat(opinions.getInt("neutral"), is(2));
    }

    @Test
    public void canFetchMultipleHour() throws JSONException {
        final ClientResource resource = restlet.newClientResource("/feeds/" + uri + "/stats:" + new DateTime().minus(1).getMillis() + "." + new DateTime().plus(1).plusHours(1).getMillis() + ";hour");
        time.waitMinutes(60);
        TestFactories.statistics().newFeedStat(uri);
        time.waitDays(1);

        final JsonRepresentation representation = (JsonRepresentation) resource.get();

        final JSONObject json = representation.getJsonObject();
        final JSONArray stats = json.getJSONArray("stats");
        assertThat(stats.length(), is(2));
    }

    @Test
    public void canFetchMultipleHourWithDifferentDays() throws JSONException {
        time.waitDays(1);
        time.waitMinutes(180);
        final Statistics otherStatistics = TestFactories.statistics().newFeedStat(uri);
        final ClientResource resource = restlet.newClientResource("/feeds/" + uri + "/stats:" + statistics.getDate().getMillis() + "." + otherStatistics.getDate().plus(1).getMillis() + ";hour");
        time.waitDays(1);

        final JsonRepresentation representation = (JsonRepresentation) resource.get();

        final JSONObject json = representation.getJsonObject();
        final JSONArray stats = json.getJSONArray("stats");
        assertThat(stats.length(), is(2));
    }

    @Test
    public void canFetchGoodGranularity() throws JSONException {
        TestFactories.statistics().newFeedStat(uri, Granularity.day);
        final ClientResource resource = restlet.newClientResource("/feeds/" + uri + "/stats:" + new DateTime().getMillis() + "." + new DateTime().plus(1).getMillis() + ";day");
        time.waitDays(1);

        final JsonRepresentation representation = (JsonRepresentation) resource.get();

        final JSONObject json = representation.getJsonObject();
        assertThat(json.getString("granularity"), is(Granularity.day.toString()));
        final JSONArray stats = json.getJSONArray("stats");
        assertThat(stats.length(), is(1));
    }

    private Statistics statistics;
    private String uri;
}
