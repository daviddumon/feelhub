package com.feelhub.web.resources.json;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
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

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsJsonFeelingsResource {

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
        final ClientResource feelingsResource = restlet.newClientResource("/json/feelings");

        feelingsResource.get();

        assertThat(feelingsResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetWithQueryString() {
        final ClientResource feelingsResource = restlet.newClientResource("/json/feelings?q=test");

        feelingsResource.get();

        assertThat(feelingsResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetAnFeeling() throws IOException, JSONException {
        TestFactories.feelings().newFeeling();
        final ClientResource resource = restlet.newClientResource("/json/feelings?skip=0&limit=1");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(1));
    }

    @Test
    public void defaultLimitIs100() throws IOException, JSONException {
        TestFactories.feelings().newFeelings(150);
        final ClientResource clientResource = restlet.newClientResource("/json/feelings?skip=0");

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length(), is(100));
    }

    @Test
    public void defaultSkipIs0() throws IOException, JSONException {
        TestFactories.feelings().newFeelings(100);
        final ClientResource clientResource = restlet.newClientResource("/json/feelings?limit=10");

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(((JSONObject) jsonArray.get(0)).get("text").toString(), is("i0"));
    }

    @Test
    public void canGetMultipleFeelings() throws JSONException, IOException {
        final ClientResource resource = restlet.newClientResource("/json/feelings?skip=0&limit=10");
        TestFactories.feelings().newFeeling();
        TestFactories.feelings().newFeeling();
        TestFactories.feelings().newFeeling();

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(3));
    }

    @Test
    public void canGetMultipleFeelingsWithSkip() throws JSONException, IOException {
        TestFactories.feelings().newFeeling();
        TestFactories.feelings().newFeeling();
        TestFactories.feelings().newFeeling();
        final ClientResource resource = restlet.newClientResource("/json/feelings?skip=1");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(2));
    }

    @Test
    public void canThrowFeelhubJsonException() {
        final ClientResource resource = restlet.newClientResource("/json/feelings?skip=0&limit=101");

        resource.get();

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void canGetFeelingForTopic() throws IOException, JSONException {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.feelings().newFeelings(10);
        TestFactories.feelings().newFeelings(topic, 10);
        TestFactories.feelings().newFeelings(10);
        final ClientResource clientResource = restlet.newClientResource("/json/feelings?topicId=" + topic.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length(), is(10));
    }

    @Test
    public void canGetFeelingWithALanguage() throws IOException, JSONException {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.keywords().newWord("keyword", FeelhubLanguage.reference(), topic);
        TestFactories.keywords().newWord("mot", FeelhubLanguage.forString("fr"), topic);
        final Sentiment sentiment = TestFactories.sentiments().newSentiment(topic, SentimentValue.good);
        TestFactories.feelings().newFeeling("my feeling", sentiment);
        final ClientResource clientResource = restlet.newClientResource("/json/feelings?topicId=" + topic.getId() + "&languageCode=fr");

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject jsonFeeling = jsonArray.getJSONObject(0);
        assertThat(jsonFeeling.get("text").toString(), is("my feeling"));
        final JSONArray jsonTopicDatas = jsonFeeling.getJSONArray("topicDatas");
        assertThat(jsonTopicDatas.length(), is(1));
        final JSONObject jsonTopicData = jsonTopicDatas.getJSONObject(0);
        assertThat(jsonTopicData.get("topicId").toString(), is(topic.getId().toString()));
        assertThat(jsonTopicData.get("sentimentValue").toString(), is(sentiment.getSentimentValue().toString()));
        assertThat(jsonTopicData.get("keywordValue").toString(), is("mot"));
        assertThat(jsonTopicData.get("languageCode").toString(), is("fr"));
    }

    @Test
    public void useDefaultLanguage() throws IOException, JSONException {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.keywords().newWord("keyword", FeelhubLanguage.reference(), topic);
        TestFactories.keywords().newWord("mot", FeelhubLanguage.forString("fr"), topic);
        final Sentiment sentiment = TestFactories.sentiments().newSentiment(topic, SentimentValue.good);
        TestFactories.feelings().newFeeling("my feeling", sentiment);
        final ClientResource clientResource = restlet.newClientResource("/json/feelings?topicId=" + topic.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject jsonFeeling = jsonArray.getJSONObject(0);
        assertThat(jsonFeeling.get("text").toString(), is("my feeling"));
        final JSONArray jsonTopicDatas = jsonFeeling.getJSONArray("topicDatas");
        assertThat(jsonTopicDatas.length(), is(1));
        final JSONObject jsonTopicData = jsonTopicDatas.getJSONObject(0);
        assertThat(jsonTopicData.get("topicId").toString(), is(topic.getId().toString()));
        assertThat(jsonTopicData.get("sentimentValue").toString(), is(sentiment.getSentimentValue().toString()));
        assertThat(jsonTopicData.get("keywordValue").toString(), is("keyword"));
        assertThat(jsonTopicData.get("languageCode").toString(), is("en"));
    }

    @Test
    public void hasIllustrationData() throws IOException, JSONException {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.illustrations().newIllustration(topic, "link");
        TestFactories.keywords().newWord("keyword", FeelhubLanguage.reference(), topic);
        final Sentiment sentiment = TestFactories.sentiments().newSentiment(topic, SentimentValue.good);
        TestFactories.feelings().newFeeling("my feeling", sentiment);
        final ClientResource clientResource = restlet.newClientResource("/json/feelings?topicId=" + topic.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject jsonTopicData = jsonArray.getJSONObject(0).getJSONArray("topicDatas").getJSONObject(0);
        assertThat(jsonTopicData.get("illustrationLink").toString(), is("link"));
    }

    @Test
    public void hasLanguageCodeData() throws IOException, JSONException {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.illustrations().newIllustration(topic, "link");
        TestFactories.keywords().newWord("keyword", FeelhubLanguage.reference(), topic);
        final Sentiment sentiment = TestFactories.sentiments().newSentiment(topic, SentimentValue.good);
        TestFactories.feelings().newFeeling("my feeling", sentiment);
        final ClientResource clientResource = restlet.newClientResource("/json/feelings?topicId=" + topic.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.getJSONObject(0).get("languageCode").toString(), is(FeelhubLanguage.reference().getCode()));
    }

    @Test
    public void hasUserIdData() throws IOException, JSONException {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.illustrations().newIllustration(topic, "link");
        TestFactories.keywords().newWord("keyword", FeelhubLanguage.reference(), topic);
        final Sentiment sentiment = TestFactories.sentiments().newSentiment(topic, SentimentValue.good);
        final Feeling feeling = TestFactories.feelings().newFeeling("my feeling", sentiment);
        final ClientResource clientResource = restlet.newClientResource("/json/feelings?topicId=" + topic.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.getJSONObject(0).get("userId").toString(), is(feeling.getUserId()));
    }
}
