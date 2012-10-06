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
    public void errorIfBadJudgment() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithBadJudgment();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation);

        assertThat(opinionsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void doNotCreateOpinionOnError() {
        final JsonRepresentation jsonRepresentation = badJsonOpinionWithBadJudgment();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation);

        assertThat(Repositories.opinions().getAll().size(), is(0));
    }

    @Test
    public void postingAGoodFormCreateAnOpinion() {
        final JsonRepresentation jsonRepresentation = goodJsonOpinion();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation);

        assertThat(Repositories.opinions().getAll().size(), is(1));
    }

    @Test
    public void canCreateKeywordsFromOpinion() {
        final JsonRepresentation jsonRepresentation = goodJsonOpinion();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation);

        assertThat(Repositories.keywords().getAll().size(), is(2));
    }

    @Test
    public void useExistingKeywords() {
        TestFactories.keywords().newKeyword("keyword", SteambeatLanguage.reference());
        TestFactories.keywords().newKeyword("mot", SteambeatLanguage.forString("fr"));
        final JsonRepresentation jsonRepresentation = goodJsonOpinion();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation);

        assertThat(Repositories.keywords().getAll().size(), is(2));
    }

    @Test
    public void addAJudgmentForEachKeywordAndFeeling() {
        final JsonRepresentation jsonRepresentation = goodJsonOpinion();
        final ClientResource opinionsResource = restlet.newClientResource("/json/createopinion");

        opinionsResource.post(jsonRepresentation);

        assertThat(Repositories.opinions().getAll().get(0).getJudgments().size(), is(2));
    }

    private JsonRepresentation goodJsonOpinion() {
        JSONObject opinion = new JSONObject();
        try {
            opinion.put("text", "my opinion");
            final JSONObject judgment1 = new JSONObject();
            judgment1.put("feeling", "good");
            judgment1.put("keywordValue", "keyword");
            judgment1.put("languageCode", "en");
            final JSONObject judgment2 = new JSONObject();
            judgment2.put("feeling", "bad");
            judgment2.put("keywordValue", "mot");
            judgment2.put("languageCode", "fr");
            final JSONArray judgments = new JSONArray();
            judgments.put(judgment1);
            judgments.put(judgment2);
            opinion.put("judgments", judgments);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JsonRepresentation(opinion);
    }

    private JsonRepresentation badJsonOpinionWithoutText() {
        JSONObject opinion = new JSONObject();
        try {
            final JSONArray judgments = new JSONArray();
            opinion.put("judgments", judgments);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JsonRepresentation(opinion);
    }

    private JsonRepresentation badJsonOpinionWithBadJudgment() {
        JSONObject opinion = new JSONObject();
        try {
            opinion.put("text", "my opinion");
            final JSONObject judgment1 = new JSONObject();
            judgment1.put("keywordValue", "keyword");
            judgment1.put("languageCode", "en");
            final JSONArray judgments = new JSONArray();
            judgments.put(judgment1);
            opinion.put("judgments", judgments);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JsonRepresentation(opinion);
    }
}
