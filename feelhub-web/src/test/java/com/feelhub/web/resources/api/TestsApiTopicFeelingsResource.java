package com.feelhub.web.resources.api;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;

import java.io.IOException;
import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsApiTopicFeelingsResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithDomainEvent events = new WithDomainEvent();

    @Test
    public void feelingsResourceIsMapped() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final ClientResource feelingsResource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/feelings");

        feelingsResource.get();

        assertThat(feelingsResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void canGetWithQueryString() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final ClientResource feelingsResource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/feelings?q=test");

        feelingsResource.get();

        assertThat(feelingsResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void canThrowException() {
        final ClientResource feelingsResource = restlet.newClientResource("/api/topic/" + UUID.randomUUID() + "/feelings");

        feelingsResource.get();

        assertThat(feelingsResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void canGetAnFeeling() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.feelings().newFeeling(realTopic.getId(), "text");
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/feelings?skip=0&limit=1");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(1);
    }

    @Test
    public void defaultLimitIs100() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.feelings().newFeelings(realTopic.getId(), 150);
        final ClientResource clientResource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/feelings?skip=0");

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length()).isEqualTo(100);
    }

    @Test
    public void defaultSkipIs0() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.feelings().newFeelings(realTopic.getId(), 100);
        final ClientResource clientResource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/feelings?limit=10");

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(((JSONObject) jsonArray.get(0)).get("text").toString()).isEqualTo("i0");
    }

    @Test
    public void canGetMultipleFeelings() throws JSONException, IOException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/feelings?skip=0&limit=10");
        TestFactories.feelings().newFeeling(realTopic.getId(), "text");
        TestFactories.feelings().newFeeling(realTopic.getId(), "text");
        TestFactories.feelings().newFeeling(realTopic.getId(), "text");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(3);
    }

    @Test
    public void canGetMultipleFeelingsWithSkip() throws JSONException, IOException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/feelings?skip=1");
        TestFactories.feelings().newFeeling(realTopic.getId(), "text");
        TestFactories.feelings().newFeeling(realTopic.getId(), "text");
        TestFactories.feelings().newFeeling(realTopic.getId(), "text");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(2);
    }

    @Test
    public void canThrowFeelhubJsonException() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/feelings?skip=0&limit=101");

        resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void canGetFeelingForTopic() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.feelings().newFeelings(10);
        TestFactories.feelings().newFeelings(realTopic.getId(), 10);
        TestFactories.feelings().newFeelings(10);
        final ClientResource clientResource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/feelings");

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length()).isEqualTo(10);
    }

    @Test
    @Ignore
    public void hasGoodTopicData() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Sentiment sentiment = TestFactories.sentiments().newSentiment(realTopic, SentimentValue.good);
        TestFactories.feelings().newFeeling("my feeling", sentiment);
        final ClientResource clientResource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/feelings");

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject jsonFeeling = jsonArray.getJSONObject(0);
        assertThat(jsonFeeling.get("text").toString()).isEqualTo("my feeling");
        final JSONArray jsonKeywordDatas = jsonFeeling.getJSONArray("topicDatas");
        assertThat(jsonKeywordDatas.length()).isEqualTo(1);
        final JSONObject jsonKeywordData = jsonKeywordDatas.getJSONObject(0);
        assertThat(jsonKeywordData.get("id").toString()).isEqualTo(realTopic.getId().toString());
        assertThat(jsonKeywordData.get("sentimentValue").toString()).isEqualTo(sentiment.getSentimentValue().toString());
        assertThat(jsonKeywordData.get("description").toString()).isEqualTo("Description-reference");
    }

    @Test
    public void hasIllustrationData() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.illustrations().newIllustration(realTopic.getId(), "link");
        final Sentiment sentiment = TestFactories.sentiments().newSentiment(realTopic, SentimentValue.good);
        TestFactories.feelings().newFeeling("my feeling", sentiment);
        final ClientResource clientResource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/feelings");

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject jsonKeywordData = jsonArray.getJSONObject(0).getJSONArray("topicDatas").getJSONObject(0);
        assertThat(jsonKeywordData.get("illustrationLink").toString()).isEqualTo("link");
    }

    @Test
    public void hasUserIdData() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.illustrations().newIllustration(realTopic.getId(), "link");
        final Sentiment sentiment = TestFactories.sentiments().newSentiment(realTopic, SentimentValue.good);
        final Feeling feeling = TestFactories.feelings().newFeeling("my feeling", sentiment);
        final ClientResource clientResource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/feelings");

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.getJSONObject(0).get("userId").toString()).isEqualTo(feeling.getUserId().toString());
    }
}
