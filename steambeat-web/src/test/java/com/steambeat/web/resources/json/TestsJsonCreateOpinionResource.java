package com.steambeat.web.resources.json;

import com.steambeat.application.SessionService;
import com.steambeat.domain.eventbus.WithDomainEvent;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.session.Session;
import com.steambeat.domain.user.User;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import com.steambeat.web.*;
import org.joda.time.DateTime;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.restlet.data.Status;
import org.restlet.engine.util.CookieSeries;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.*;

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
        final SessionService sessionService = new SessionService();
        user = TestFactories.users().createUser("mail@mail.com", "full name");
        final Session session = sessionService.createSession(user, new DateTime().plusHours(1));
        final org.restlet.data.Cookie cookie = new org.restlet.data.Cookie(1, "id", "mail@mail.com");
        final org.restlet.data.Cookie sessionCookie = new org.restlet.data.Cookie(1, "session", session.getToken().toString());
        cookies = new CookieSeries();
        cookies.add(cookie);
        cookies.add(sessionCookie);
    }

    @Test
    public void canPostOpinion() {
        final JsonRepresentation jsonRepresentation = goodJsonOpinion();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation, cookies);

        assertThat(opinionsResource.getStatus(), is(Status.SUCCESS_CREATED));
    }

    @Test
    public void errorIfNotJson() {
        final StringRepresentation jsonRepresentation = new StringRepresentation("representation");
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation, cookies);

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void errorIfMissingText() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithoutText();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation, cookies);

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void errorIfMissingFeeling() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithoutFeeling();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation, cookies);

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void errorIfMissingKeywordValue() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithoutKeywordValue();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation, cookies);

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void errorIfMissingLanguageCode() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithoutLanguageCode();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation, cookies);

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void errorIfMissingUserLanguageCode() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithoutUserLanguageCode();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation, cookies);

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void doNotCreateOpinionOnError() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithoutFeeling();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation, cookies);

        assertThat(Repositories.opinions().getAll().size(), is(0));
    }

    @Test
    public void mustBeAuthentificated() {
        final JsonRepresentation jsonRepresentation = goodJsonOpinion();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation);

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_UNAUTHORIZED));
    }

    @Test
    public void postAnOpinionRequestEvent() {
        events.capture(OpinionRequestEvent.class);
        final JsonRepresentation jsonRepresentation = goodJsonOpinion();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation, cookies);

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
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        final JsonRepresentation jsonResponse = (JsonRepresentation) opinionsResource.post(jsonRepresentation, cookies);

        final OpinionRequestEvent opinionRequestEvent = events.lastEvent(OpinionRequestEvent.class);
        final JSONObject jsonData = jsonResponse.getJsonObject();
        assertThat(jsonData.get("referenceId").toString(), is(opinionRequestEvent.getOpinionId()));
    }

    private JsonRepresentation goodJsonOpinion() {
        JSONObject opinion = new JSONObject();
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
        JSONObject opinion = new JSONObject();
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
        JSONObject opinion = new JSONObject();
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
        JSONObject opinion = new JSONObject();
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
        JSONObject opinion = new JSONObject();
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
        JSONObject opinion = new JSONObject();
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

    private CookieSeries cookies;
    private User user;
}