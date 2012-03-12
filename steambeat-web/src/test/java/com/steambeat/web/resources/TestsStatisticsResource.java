package com.steambeat.web.resources;

import com.steambeat.domain.opinion.*;
import com.steambeat.domain.statistics.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.TestWithMongoRepository;
import com.steambeat.test.SystemTime;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.web.*;
import org.joda.time.DateTime;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.restlet.data.*;
import org.restlet.representation.Representation;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Ignore
public class TestsStatisticsResource extends TestWithMongoRepository {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canRepresent() {
        final WebPage webPage = TestFactories.subjects().newWebPage();
        final ClientResource resource = restlet.newClientResource("/statistics?start=0&end=0&granularity=hour&subjectId=" + webPage.getId());

        final Representation representation = resource.get();

        assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
        assertThat(representation.getMediaType(), is(MediaType.APPLICATION_JSON));
    }

    @Test
    public void throwJsonExceptionIfNoGranularityParameter() {
        final ClientResource resource = restlet.newClientResource("/statistics?start=0&end=0&subjectId=" + UUID.randomUUID());

        resource.get();

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void throwJsonExceptionIfNoStartParameter() {
        final ClientResource resource = restlet.newClientResource("/statistics?end=0&granularity=hour&subjectId=" + UUID.randomUUID());

        resource.get();

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void throwJsonExceptionIfNoEndParameter() {
        final ClientResource resource = restlet.newClientResource("/statistics?start=0&granularity=hour&subjectId=" + UUID.randomUUID());

        resource.get();

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void throwJsonExceptionIfNoSubjectIdParameter() {
        final ClientResource resource = restlet.newClientResource("/statistics?start=0&end=0&granularity=hour");

        resource.get();

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void canFetchSingleHour() throws JSONException, IOException {
        final WebPage webPage = TestFactories.subjects().newWebPage();
        final Statistics statistics = TestFactories.statistics().newWebPageStat(webPage, Granularity.hour);
        statistics.incrementJudgmentCount(new Judgment(webPage, Feeling.good));
        statistics.incrementJudgmentCount(new Judgment(webPage, Feeling.bad));
        statistics.incrementJudgmentCount(new Judgment(webPage, Feeling.bad));
        final ClientResource resource = restlet.newClientResource("/statistics?" + "start=" + new DateTime().minus(1).getMillis() + "&end=" + new DateTime().plus(1).getMillis() + "&granularity=hour" + "&subjectId=" + webPage.getId());
        time.waitDays(1);

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        final JSONArray stats = new JSONArray(representation.getText());
        assertThat(stats.length(), is(1));
        assertThat(stats.getJSONObject(0).get("good").toString(), is("1"));
        assertThat(stats.getJSONObject(0).get("bad").toString(), is("2"));
    }

    //@Test
    //public void canFetchMultipleHour() throws JSONException {
    //    final ClientResource resource = restlet.newClientResource("/webpages/" + uri + "/stats:" + new DateTime().minus(1).getMillis() + "." + new DateTime().plus(1).plusHours(1).getMillis() + ";hour");
    //    time.waitMinutes(60);
    //    TestFactories.statistics().newWebPageStat(uri);
    //    time.waitDays(1);
    //
    //    final JsonRepresentation representation = (JsonRepresentation) resource.get();
    //
    //    final JSONObject json = representation.getJsonObject();
    //    final JSONArray stats = json.getJSONArray("stats");
    //    assertThat(stats.length(), is(2));
    //}
    //
    //@Test
    //public void canFetchMultipleHourWithDifferentDays() throws JSONException {
    //    time.waitDays(1);
    //    time.waitMinutes(180);
    //    final Statistics otherStatistics = TestFactories.statistics().newWebPageStat(uri);
    //    final ClientResource resource = restlet.newClientResource("/webpages/" + uri + "/stats:" + statistics.getDate().getMillis() + "." + otherStatistics.getDate().plus(1).getMillis() + ";hour");
    //    time.waitDays(1);
    //
    //    final JsonRepresentation representation = (JsonRepresentation) resource.get();
    //
    //    final JSONObject json = representation.getJsonObject();
    //    final JSONArray stats = json.getJSONArray("stats");
    //    assertThat(stats.length(), is(2));
    //}
    //
    //@Test
    //public void canFetchGoodGranularity() throws JSONException {
    //    TestFactories.statistics().newWebPageStat(uri, Granularity.day);
    //    final ClientResource resource = restlet.newClientResource("/webpages/" + uri + "/stats:" + new DateTime().getMillis() + "." + new DateTime().plus(1).getMillis() + ";day");
    //    time.waitDays(1);
    //
    //    final JsonRepresentation representation = (JsonRepresentation) resource.get();
    //
    //    final JSONObject json = representation.getJsonObject();
    //    assertThat(json.getString("granularity"), is(Granularity.day.toString()));
    //    final JSONArray stats = json.getJSONArray("stats");
    //    assertThat(stats.length(), is(1));
    //}
}
