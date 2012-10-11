package com.steambeat.web.resources.json;

import com.steambeat.domain.eventbus.WithDomainEvent;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import com.steambeat.web.*;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.StringRepresentation;

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

    @Test
    public void canPostOpinion() {
        final JsonRepresentation jsonRepresentation = goodJsonOpinion();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation);

        assertThat(opinionsResource.getStatus(), is(Status.SUCCESS_CREATED));
    }

    @Test
    public void errorIfNotJson() {
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(new StringRepresentation("representation"));

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void errorIfMissingText() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithoutText();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation);

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void errorIfMissingFeeling() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithoutFeeling();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation);

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void errorIfMissingKeywordValue() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithoutKeywordValue();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation);

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void errorIfMissingLanguageCode() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithoutLanguageCode();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation);

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void errorIfMissingUserLanguageCode() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithoutUserLanguageCode();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation);

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void doNotCreateOpinionOnError() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithoutFeeling();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation);

        assertThat(Repositories.opinions().getAll().size(), is(0));
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
}