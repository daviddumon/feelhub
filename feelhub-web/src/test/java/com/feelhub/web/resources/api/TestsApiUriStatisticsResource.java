package com.feelhub.web.resources.api;

import com.feelhub.domain.feeling.*;
import com.feelhub.domain.keyword.uri.Uri;
import com.feelhub.domain.statistics.*;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.feelhub.web.*;
import org.joda.time.DateTime;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.restlet.data.*;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;

import java.io.IOException;
import java.util.UUID;

import static org.fest.assertions.Assertions.*;


public class TestsApiUriStatisticsResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void apiUriStatisticsResourceIsMapped() {
        final Uri uri = TestFactories.keywords().newUri();
        final ClientResource resource = restlet.newClientResource("/api/uri/" + uri.getId() + "/statistics?start=0&end=0&granularity=hour");

        final Representation representation = resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.SUCCESS_OK);
        assertThat(representation.getMediaType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    public void throwApiExceptionIfNoGranularityParameter() {
        final Uri uri = TestFactories.keywords().newUri();
        final ClientResource resource = restlet.newClientResource("/api/uri/" + uri.getId() + "/statistics?start=0&end=0");

        resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void throwApiExceptionIfNoStartParameter() {
        final Uri uri = TestFactories.keywords().newUri();
        final ClientResource resource = restlet.newClientResource("/api/uri/" + uri.getId() + "/statistics?end=0&granularity=hour");

        resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void throwApiExceptionIfNoEndParameter() {
        final Uri uri = TestFactories.keywords().newUri();
        final ClientResource resource = restlet.newClientResource("/api/uri/" + uri.getId() + "/statistics?start=0&granularity=hour");

        resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void throwApiExceptionIfNoUriDoesNotExist() {
        final ClientResource resource = restlet.newClientResource("/api/uri/" + UUID.randomUUID() + "/statistics?start=0&end=0&granularity=hour");

        resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void canFetchSingleHour() throws JSONException, IOException {
        final Uri uri = TestFactories.keywords().newUri();
        final Statistics statistics = TestFactories.statistics().newStatistics(uri.getTopicId(), Granularity.hour);
        statistics.incrementSentimentCount(new Sentiment(uri.getTopicId(), SentimentValue.good));
        statistics.incrementSentimentCount(new Sentiment(uri.getTopicId(), SentimentValue.bad));
        statistics.incrementSentimentCount(new Sentiment(uri.getTopicId(), SentimentValue.bad));
        final ClientResource resource = restlet.newClientResource("/api/uri/" + uri.getId() + "/statistics?start=" + new DateTime().minus(1).getMillis() + "&end=" + new DateTime().plus(1).getMillis() + "&granularity=hour");
        time.waitDays(1);

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        final JSONArray stats = new JSONArray(representation.getText());
        assertThat(stats.length()).isEqualTo(1);
        assertThat(stats.getJSONObject(0).get("good").toString()).isEqualTo("1");
        assertThat(stats.getJSONObject(0).get("bad").toString()).isEqualTo("2");
    }

    @Test
    public void canFetchMultipleHour() throws JSONException, IOException {
        final Uri uri = TestFactories.keywords().newUri();
        final Statistics stat1 = TestFactories.statistics().newStatistics(uri.getTopicId(), Granularity.hour);
        time.waitHours(1);
        final Statistics stat2 = TestFactories.statistics().newStatistics(uri.getTopicId(), Granularity.hour);
        final ClientResource resource = restlet.newClientResource("/api/uri/" + uri.getId() + "/statistics?start=" + stat1.getDate().minus(1).getMillis() + "&end=" + stat2.getDate().plus(1).getMillis() + "&granularity=hour");
        time.waitDays(1);

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        final JSONArray stats = new JSONArray(representation.getText());
        assertThat(stats.length()).isEqualTo(2);
    }

    @Test
    public void canFetchMultipleHourWithDifferentDays() throws JSONException, IOException {
        final Uri uri = TestFactories.keywords().newUri();
        final Statistics stat1 = TestFactories.statistics().newStatistics(uri.getTopicId(), Granularity.day);
        time.waitMonths(1);
        final Statistics stat2 = TestFactories.statistics().newStatistics(uri.getTopicId(), Granularity.day);
        final ClientResource resource = restlet.newClientResource("/api/uri/" + uri.getId() + "/statistics?start=" + stat1.getDate().minus(1).getMillis() + "&end=" + stat2.getDate().plus(1).getMillis() + "&granularity=day");
        time.waitDays(1);

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        final JSONArray stats = new JSONArray(representation.getText());
        assertThat(stats.length()).isEqualTo(2);
    }

    @Test
    @Ignore
    public void canGetStatisticsForWorld() throws IOException, JSONException {
        //final World world = TestFactories.topics().newWorld();
        //final Statistics stat = TestFactories.statistics().newStatistics(world, Granularity.all);
        //time.waitMonths(1);
        //final ClientResource resource = restlet.newClientResource("/api/statistics?" + "start=" + stat.getDate().minus(1).getMillis() + "&end=" + stat.getDate().plus(1).getMillis() + "&granularity=all" + "&topicId=" + world.getId());
        //
        //final FeelhubTemplateRepresentation representation = (FeelhubTemplateRepresentation) resource.get();
        //
        //final JSONArray stats = new JSONArray(representation.getText());
        //assertThat(stats.length(), is(1));
    }
}
