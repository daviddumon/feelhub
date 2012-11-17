package com.feelhub.web.resources.api;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.WebApplicationTester;
import com.feelhub.web.authentification.*;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.restlet.*;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsApiCreateFeelingResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithDomainEvent events = new WithDomainEvent();

    @Before
    public void before() {
        user = TestFactories.users().createFakeUser("mail@mail.com", "full name");
        CurrentUser.set(new WebUser(user, true));
        feelingsResource = new ApiCreateFeelingResource();
        feelingsResource.setResponse(new Response(new Request()));
    }

    @After
    public void tearDown() throws Exception {
        CurrentUser.set(null);
    }

    @Test
    public void canPostFeeling() {
        final JsonRepresentation jsonRepresentation = goodJsonFeeling();

        feelingsResource.add(jsonRepresentation);

        assertThat(feelingsResource.getStatus(), is(Status.SUCCESS_CREATED));
    }

    @Test
    public void errorIfMissingText() {
        final JsonRepresentation jsonRepresentation = badJsonFeelingWithoutText();

        feelingsResource.add(jsonRepresentation);

        assertThat(feelingsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void errorIfMissingSentimentValue() {
        final JsonRepresentation jsonRepresentation = badJsonFeelingWithoutSentimentValue();

        feelingsResource.add(jsonRepresentation);

        assertThat(feelingsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void errorIfMissingKeywordValue() {
        final JsonRepresentation jsonRepresentation = badJsonFeelingWithoutKeywordValue();

        feelingsResource.add(jsonRepresentation);

        assertThat(feelingsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void errorIfMissingLanguageCode() {
        final JsonRepresentation jsonRepresentation = badJsonFeelingWithoutLanguageCode();

        feelingsResource.add(jsonRepresentation);

        assertThat(feelingsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void errorIfMissingUserLanguageCode() {
        final JsonRepresentation jsonRepresentation = badJsonFeelingWithoutUserLanguageCode();

        feelingsResource.add(jsonRepresentation);

        assertThat(feelingsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void doNotCreateFeelingOnError() {
        final JsonRepresentation jsonRepresentation = badJsonFeelingWithoutSentimentValue();

        feelingsResource.add(jsonRepresentation);

        assertThat(Repositories.feelings().getAll().size(), is(0));
    }

    @Test
    public void mustBeAuthentificated() {
        CurrentUser.set(WebUser.anonymous());
        final JsonRepresentation jsonRepresentation = goodJsonFeeling();

        feelingsResource.add(jsonRepresentation);

        assertThat(feelingsResource.getStatus(), is(Status.CLIENT_ERROR_UNAUTHORIZED));
    }

    @Test
    public void postAnFeelingRequestEvent() {
        events.capture(FeelingRequestEvent.class);
        final JsonRepresentation jsonRepresentation = goodJsonFeeling();

        feelingsResource.add(jsonRepresentation);

        final FeelingRequestEvent feelingRequestEvent = events.lastEvent(FeelingRequestEvent.class);
        assertThat(feelingRequestEvent, notNullValue());
        assertThat(feelingRequestEvent.getSentimentValue(), is(SentimentValue.valueOf("good")));
        assertThat(feelingRequestEvent.getText(), is("my feeling +sentiment"));
        assertThat(feelingRequestEvent.getKeywordValue(), is("keyword"));
        assertThat(feelingRequestEvent.getLanguage(), is(FeelhubLanguage.fromCode("en")));
        assertThat(feelingRequestEvent.getUserLanguage(), is(FeelhubLanguage.fromCode("fr")));
        assertThat(feelingRequestEvent.getUserId(), is(user.getId()));
    }

    @Test
    public void returnFeelingId() throws JSONException {
        events.capture(FeelingRequestEvent.class);
        final JsonRepresentation jsonRepresentation = goodJsonFeeling();

        final JsonRepresentation jsonResponse = feelingsResource.add(jsonRepresentation);

        final FeelingRequestEvent feelingRequestEvent = events.lastEvent(FeelingRequestEvent.class);
        final JSONObject jsonData = jsonResponse.getJsonObject();
        assertThat(jsonData.get("id").toString(), is(feelingRequestEvent.getFeelingId().toString()));
    }

    private JsonRepresentation goodJsonFeeling() {
        final JSONObject feeling = new JSONObject();
        try {
            feeling.put("text", "my feeling +sentiment");
            feeling.put("sentimentValue", "good");
            feeling.put("keywordValue", "keyword");
            feeling.put("languageCode", "en");
            feeling.put("userLanguageCode", "fr");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JsonRepresentation(feeling);
    }

    private JsonRepresentation badJsonFeelingWithoutText() {
        final JSONObject feeling = new JSONObject();
        try {
            feeling.put("sentimentValue", "good");
            feeling.put("keywordValue", "keyword");
            feeling.put("languageCode", "en");
            feeling.put("userLanguageCode", "fr");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JsonRepresentation(feeling);
    }

    private JsonRepresentation badJsonFeelingWithoutSentimentValue() {
        final JSONObject feeling = new JSONObject();
        try {
            feeling.put("text", "my feeling");
            feeling.put("keywordValue", "keyword");
            feeling.put("languageCode", "en");
            feeling.put("userLanguageCode", "fr");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JsonRepresentation(feeling);
    }

    private JsonRepresentation badJsonFeelingWithoutKeywordValue() {
        final JSONObject feeling = new JSONObject();
        try {
            feeling.put("text", "my feeling");
            feeling.put("sentimentValue", "good");
            feeling.put("languageCode", "en");
            feeling.put("userLanguageCode", "fr");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JsonRepresentation(feeling);
    }

    private JsonRepresentation badJsonFeelingWithoutLanguageCode() {
        final JSONObject feeling = new JSONObject();
        try {
            feeling.put("text", "my feeling");
            feeling.put("sentimentValue", "good");
            feeling.put("keywordValue", "keyword");
            feeling.put("userLanguageCode", "fr");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JsonRepresentation(feeling);
    }

    private JsonRepresentation badJsonFeelingWithoutUserLanguageCode() {
        final JSONObject feeling = new JSONObject();
        try {
            feeling.put("text", "my feeling");
            feeling.put("sentimentValue", "good");
            feeling.put("keywordValue", "keyword");
            feeling.put("languageCode", "en");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JsonRepresentation(feeling);
    }

    private User user;
    private ApiCreateFeelingResource feelingsResource;
}