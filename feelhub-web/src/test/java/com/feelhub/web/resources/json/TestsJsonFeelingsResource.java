package com.feelhub.web.resources.json;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.reference.Reference;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
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
    public void canGetFeelingForSubject() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.feelings().newFeelings(10);
        TestFactories.feelings().newFeelings(reference, 10);
        TestFactories.feelings().newFeelings(10);
        final ClientResource clientResource = restlet.newClientResource("/json/feelings?referenceId=" + reference.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length(), is(10));
    }

    @Test
    public void canGetFeelingWithALanguage() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.keywords().newKeyword("keyword", FeelhubLanguage.reference(), reference);
        TestFactories.keywords().newKeyword("mot", FeelhubLanguage.forString("fr"), reference);
        final Sentiment sentiment = TestFactories.sentiments().newSentiment(reference, SentimentValue.good);
        TestFactories.feelings().newFeeling("my feeling", sentiment);
        final ClientResource clientResource = restlet.newClientResource("/json/feelings?referenceId=" + reference.getId() + "&languageCode=fr");

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject jsonFeeling = jsonArray.getJSONObject(0);
        assertThat(jsonFeeling.get("text").toString(), is("my feeling"));
        final JSONArray jsonReferenceDatas = jsonFeeling.getJSONArray("referenceDatas");
        assertThat(jsonReferenceDatas.length(), is(1));
        final JSONObject jsonReferenceData = jsonReferenceDatas.getJSONObject(0);
        assertThat(jsonReferenceData.get("referenceId").toString(), is(reference.getId().toString()));
        assertThat(jsonReferenceData.get("sentimentValue").toString(), is(sentiment.getSentimentValue().toString()));
        assertThat(jsonReferenceData.get("keywordValue").toString(), is("mot"));
        assertThat(jsonReferenceData.get("languageCode").toString(), is("fr"));
    }

    @Test
    public void useDefaultLanguage() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.keywords().newKeyword("keyword", FeelhubLanguage.reference(), reference);
        TestFactories.keywords().newKeyword("mot", FeelhubLanguage.forString("fr"), reference);
        final Sentiment sentiment = TestFactories.sentiments().newSentiment(reference, SentimentValue.good);
        TestFactories.feelings().newFeeling("my feeling", sentiment);
        final ClientResource clientResource = restlet.newClientResource("/json/feelings?referenceId=" + reference.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject jsonFeeling = jsonArray.getJSONObject(0);
        assertThat(jsonFeeling.get("text").toString(), is("my feeling"));
        final JSONArray jsonReferenceDatas = jsonFeeling.getJSONArray("referenceDatas");
        assertThat(jsonReferenceDatas.length(), is(1));
        final JSONObject jsonReferenceData = jsonReferenceDatas.getJSONObject(0);
        assertThat(jsonReferenceData.get("referenceId").toString(), is(reference.getId().toString()));
        assertThat(jsonReferenceData.get("sentimentValue").toString(), is(sentiment.getSentimentValue().toString()));
        assertThat(jsonReferenceData.get("keywordValue").toString(), is("keyword"));
        assertThat(jsonReferenceData.get("languageCode").toString(), is("en"));
    }

    @Test
    public void hasIllustrationData() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.illustrations().newIllustration(reference, "link");
        TestFactories.keywords().newKeyword("keyword", FeelhubLanguage.reference(), reference);
        final Sentiment sentiment = TestFactories.sentiments().newSentiment(reference, SentimentValue.good);
        TestFactories.feelings().newFeeling("my feeling", sentiment);
        final ClientResource clientResource = restlet.newClientResource("/json/feelings?referenceId=" + reference.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject jsonReferenceData = jsonArray.getJSONObject(0).getJSONArray("referenceDatas").getJSONObject(0);
        assertThat(jsonReferenceData.get("illustrationLink").toString(), is("link"));
    }

    @Test
    public void hasLanguageCodeData() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.illustrations().newIllustration(reference, "link");
        TestFactories.keywords().newKeyword("keyword", FeelhubLanguage.reference(), reference);
        final Sentiment sentiment = TestFactories.sentiments().newSentiment(reference, SentimentValue.good);
        TestFactories.feelings().newFeeling("my feeling", sentiment);
        final ClientResource clientResource = restlet.newClientResource("/json/feelings?referenceId=" + reference.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.getJSONObject(0).get("languageCode").toString(), is(FeelhubLanguage.reference().getCode()));
    }

    @Test
    public void hasUserIdData() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.illustrations().newIllustration(reference, "link");
        TestFactories.keywords().newKeyword("keyword", FeelhubLanguage.reference(), reference);
        final Sentiment sentiment = TestFactories.sentiments().newSentiment(reference, SentimentValue.good);
        final Feeling feeling = TestFactories.feelings().newFeeling("my feeling", sentiment);
        final ClientResource clientResource = restlet.newClientResource("/json/feelings?referenceId=" + reference.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) clientResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.getJSONObject(0).get("userId").toString(), is(feeling.getUserId()));
    }
}
