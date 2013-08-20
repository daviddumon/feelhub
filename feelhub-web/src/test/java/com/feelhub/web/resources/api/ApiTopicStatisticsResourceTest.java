package com.feelhub.web.resources.api;

import com.feelhub.domain.feeling.*;
import com.feelhub.domain.statistics.*;
import com.feelhub.domain.topic.real.RealTopic;
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

public class ApiTopicStatisticsResourceTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void apiTopicStatisticsResourceIsMapped() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/statistics?start=0&end=0&granularity=hour");

        final Representation representation = resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.SUCCESS_OK);
        assertThat(representation.getMediaType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    public void throwApiExceptionIfNoGranularityParameter() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/statistics?start=0&end=0");

        resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void throwApiExceptionIfNoStartParameter() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/statistics?end=0&granularity=hour");

        resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void throwApiExceptionIfNoEndParameter() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/statistics?start=0&granularity=hour");

        resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void throwApiExceptionIfTopicDoesNotExist() {
        final ClientResource resource = restlet.newClientResource("/api/topic/" + UUID.randomUUID() + "/statistics?start=0&end=0&granularity=hour");

        resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void canFetchSingleHour() throws JSONException, IOException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Statistics statistics = TestFactories.statistics().newStatistics(realTopic.getId(), Granularity.hour);
        statistics.incrementFeelingCount(new Sentiment(realTopic.getId(), SentimentValue.good));
        statistics.incrementFeelingCount(new Sentiment(realTopic.getId(), SentimentValue.bad));
        statistics.incrementFeelingCount(new Sentiment(realTopic.getId(), SentimentValue.bad));
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/statistics?start=" + new DateTime().minus(1).getMillis() + "&end=" + new DateTime().plus(1).getMillis() + "&granularity=hour");
        time.waitDays(1);

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        final JSONArray stats = new JSONArray(representation.getText());
        assertThat(stats.length()).isEqualTo(1);
        assertThat(stats.getJSONObject(0).get("good").toString()).isEqualTo("1");
        assertThat(stats.getJSONObject(0).get("bad").toString()).isEqualTo("2");
    }

    @Test
    public void canFetchMultipleHour() throws JSONException, IOException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Statistics stat1 = TestFactories.statistics().newStatistics(realTopic.getId(), Granularity.hour);
        time.waitHours(1);
        final Statistics stat2 = TestFactories.statistics().newStatistics(realTopic.getId(), Granularity.hour);
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/statistics?start=" + stat1.getDate().minus(1).getMillis() + "&end=" + stat2.getDate().plus(1).getMillis() + "&granularity=hour");
        time.waitDays(1);

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        final JSONArray stats = new JSONArray(representation.getText());
        assertThat(stats.length()).isEqualTo(2);
    }

    @Test
    public void canFetchMultipleHourWithDifferentDays() throws JSONException, IOException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Statistics stat1 = TestFactories.statistics().newStatistics(realTopic.getId(), Granularity.day);
        time.waitMonths(1);
        final Statistics stat2 = TestFactories.statistics().newStatistics(realTopic.getId(), Granularity.day);
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/statistics?start=" + stat1.getDate().minus(1).getMillis() + "&end=" + stat2.getDate().plus(1).getMillis() + "&granularity=day");
        time.waitDays(1);

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        final JSONArray stats = new JSONArray(representation.getText());
        assertThat(stats.length()).isEqualTo(2);
    }
}
