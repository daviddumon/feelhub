package com.feelhub.web.resources.json;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.opinion.*;
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

public class TestsJsonCreateOpinionResource {

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
        opinionsResource = new JsonCreateOpinionResource();
        opinionsResource.setResponse(new Response(new Request()));
    }

    @After
    public void tearDown() throws Exception {
        CurrentUser.set(null);
    }

    @Test
    public void canPostOpinion() {
        final JsonRepresentation jsonRepresentation = goodJsonOpinion();

        opinionsResource.add(jsonRepresentation);

        assertThat(opinionsResource.getStatus(), is(Status.SUCCESS_CREATED));
    }

    @Test
    public void errorIfMissingText() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithoutText();

        opinionsResource.add(jsonRepresentation);

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void errorIfMissingFeeling() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithoutFeeling();

        opinionsResource.add(jsonRepresentation);

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void errorIfMissingKeywordValue() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithoutKeywordValue();

        opinionsResource.add(jsonRepresentation);

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void errorIfMissingLanguageCode() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithoutLanguageCode();

        opinionsResource.add(jsonRepresentation);

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void errorIfMissingUserLanguageCode() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithoutUserLanguageCode();

        opinionsResource.add(jsonRepresentation);

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void doNotCreateOpinionOnError() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithoutFeeling();

        opinionsResource.add(jsonRepresentation);

        assertThat(Repositories.opinions().getAll().size(), is(0));
    }

    @Test
    public void mustBeAuthentificated() {
        CurrentUser.set(WebUser.anonymous());
        final JsonRepresentation jsonRepresentation = goodJsonOpinion();

        opinionsResource.add(jsonRepresentation);

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_UNAUTHORIZED));
    }

    @Test
    public void postAnOpinionRequestEvent() {
        events.capture(OpinionRequestEvent.class);
        final JsonRepresentation jsonRepresentation = goodJsonOpinion();

        opinionsResource.add(jsonRepresentation);

        final OpinionRequestEvent opinionRequestEvent = events.lastEvent(OpinionRequestEvent.class);
        assertThat(opinionRequestEvent, notNullValue());
        assertThat(opinionRequestEvent.getFeeling(), is(Feeling.valueOf("good")));
        assertThat(opinionRequestEvent.getText(), is("my opinion +judgment"));
        assertThat(opinionRequestEvent.getKeywordValue(), is("keyword"));
        assertThat(opinionRequestEvent.getLanguageCode(), is("en"));
        assertThat(opinionRequestEvent.getUserLanguageCode(), is("fr"));
        assertThat(opinionRequestEvent.getUserId(), is(user.getId()));
    }

    @Test
    public void returnOpinionId() throws JSONException {
        events.capture(OpinionRequestEvent.class);
        final JsonRepresentation jsonRepresentation = goodJsonOpinion();

        final JsonRepresentation jsonResponse = opinionsResource.add(jsonRepresentation);

        final OpinionRequestEvent opinionRequestEvent = events.lastEvent(OpinionRequestEvent.class);
        final JSONObject jsonData = jsonResponse.getJsonObject();
        assertThat(jsonData.get("id").toString(), is(opinionRequestEvent.getOpinionId().toString()));
    }

    private JsonRepresentation goodJsonOpinion() {
        final JSONObject opinion = new JSONObject();
        try {
            opinion.put("text", "my opinion +judgment");
            opinion.put("feeling", "good");
            opinion.put("keywordValue", "keyword");
            opinion.put("languageCode", "en");
            opinion.put("userLanguageCode", "fr");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JsonRepresentation(opinion);
    }

    private JsonRepresentation badJsonOpinionWithoutText() {
        final JSONObject opinion = new JSONObject();
        try {
            opinion.put("feeling", "good");
            opinion.put("keywordValue", "keyword");
            opinion.put("languageCode", "en");
            opinion.put("userLanguageCode", "fr");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JsonRepresentation(opinion);
    }

    private JsonRepresentation badJsonOpinionWithoutFeeling() {
        final JSONObject opinion = new JSONObject();
        try {
            opinion.put("text", "my opinion");
            opinion.put("keywordValue", "keyword");
            opinion.put("languageCode", "en");
            opinion.put("userLanguageCode", "fr");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JsonRepresentation(opinion);
    }

    private JsonRepresentation badJsonOpinionWithoutKeywordValue() {
        final JSONObject opinion = new JSONObject();
        try {
            opinion.put("text", "my opinion");
            opinion.put("feeling", "good");
            opinion.put("languageCode", "en");
            opinion.put("userLanguageCode", "fr");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JsonRepresentation(opinion);
    }

    private JsonRepresentation badJsonOpinionWithoutLanguageCode() {
        final JSONObject opinion = new JSONObject();
        try {
            opinion.put("text", "my opinion");
            opinion.put("feeling", "good");
            opinion.put("keywordValue", "keyword");
            opinion.put("userLanguageCode", "fr");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JsonRepresentation(opinion);
    }

    private JsonRepresentation badJsonOpinionWithoutUserLanguageCode() {
        final JSONObject opinion = new JSONObject();
        try {
            opinion.put("text", "my opinion");
            opinion.put("feeling", "good");
            opinion.put("keywordValue", "keyword");
            opinion.put("languageCode", "en");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JsonRepresentation(opinion);
    }

    private User user;
    private JsonCreateOpinionResource opinionsResource;
}