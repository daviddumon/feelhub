package com.steambeat.web.resources.json;

import com.steambeat.domain.DomainEventBus;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.concept.Concept;
import com.steambeat.domain.subject.steam.Steam;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.WithDomainEvent;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.web.*;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.restlet.Context;
import org.restlet.data.*;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpinionsResource {

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
        Repositories.subjects().add(new Steam(UUID.randomUUID()));
    }

    @Test
    public void isMapped() {
        final ClientResource opinionsResource = restlet.newClientResource("/opinions");

        opinionsResource.get();

        assertThat(opinionsResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetWithQueryString() {
        final ClientResource opinionsResource = restlet.newClientResource("/opinions?q=test");

        opinionsResource.get();

        assertThat(opinionsResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetAnOpinion() throws IOException, JSONException {
        TestFactories.opinions().newOpinion();
        final ClientResource resource = restlet.newClientResource("/opinions?skip=0&limit=1");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(1));
    }

    @Test
    public void defaultLimitIs100() throws IOException, JSONException {
        TestFactories.opinions().newOpinions(150);
        final ClientResource clientResource = restlet.newClientResource("/opinions?skip=0");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) clientResource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length(), is(100));
    }

    @Test
    public void defaultSkipIs0() throws IOException, JSONException {
        TestFactories.opinions().newOpinions(100);
        final ClientResource clientResource = restlet.newClientResource("/opinions?limit=10");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) clientResource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(((JSONObject) jsonArray.get(0)).get("text").toString(), is("i0"));
    }

    @Test
    public void canGetMultipleOpinions() throws JSONException, IOException {
        final ClientResource resource = restlet.newClientResource("/opinions?skip=0&limit=10");
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();
        DomainEventBus.INSTANCE.flush();

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(3));
    }

    @Test
    public void canGetMultipleOpinionsWithSkip() throws JSONException, IOException {
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();
        DomainEventBus.INSTANCE.flush();
        final ClientResource resource = restlet.newClientResource("/opinions?skip=1");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(2));
    }

    @Test
    public void canThrowSteambeatJsonException() {
        final ClientResource resource = restlet.newClientResource("/opinions?skip=0&limit=101");

        resource.get();

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void canGetOpinionForSubject() throws IOException, JSONException {
        final WebPage webPage = TestFactories.subjects().newWebPage();
        TestFactories.opinions().newOpinions(10);
        TestFactories.opinions().newOpinions(webPage, 10);
        TestFactories.opinions().newOpinions(10);
        final ClientResource clientResource = restlet.newClientResource("/opinions?subjectId=" + webPage.getId());

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) clientResource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length(), is(10));
    }

    @Test
    public void canPostOpinion() {
        final WebPage webPage = TestFactories.subjects().newWebPage();
        final Form form = getGoodForm();
        form.add("subjectId", webPage.getId().toString());
        form.add("redirect", "webpages");
        final ClientResource opinionsResource = restlet.newClientResource("/opinions");

        opinionsResource.post(form);

        assertThat(opinionsResource.getStatus(), is(Status.SUCCESS_CREATED));
        assertThat(Repositories.opinions().getAll().size(), is(1));
        final Opinion opinion = Repositories.opinions().getAll().get(0);
        final Judgment judgment = opinion.getJudgments().get(0);
        assertThat(judgment.getFeeling(), is(Feeling.good));
        assertThat(judgment.getSubject(), is((Subject) webPage));
    }

    @Test
    public void canPostOpinionForAConcept() {
        final Concept concept = TestFactories.subjects().newConcept();
        final Form form = getGoodForm();
        form.add("subjectId", concept.getId().toString());
        form.add("redirect", "webpages");
        final ClientResource opinionsResource = restlet.newClientResource("/opinions");

        opinionsResource.post(form);

        assertThat(opinionsResource.getStatus(), is(Status.SUCCESS_CREATED));
        assertThat(Repositories.opinions().getAll().size(), is(1));
        final Opinion opinion = Repositories.opinions().getAll().get(0);
        final Judgment judgment = opinion.getJudgments().get(0);
        assertThat(judgment.getFeeling(), is(Feeling.good));
        assertThat(judgment.getSubject(), is((Subject) concept));
    }

    @Test
    public void throwExceptionWhenUnknownWebPage() {
        final Form form = getGoodForm();
        form.add("subjectId", UUID.randomUUID().toString());
        final ClientResource resource = restlet.newClientResource("/opinions");

        resource.post(form);

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void canNotPostAOpinionWithoutFeeling() {
        final WebPage webPage = TestFactories.subjects().newWebPage();
        final Form form = new Form();
        form.add("text", "my opinion");
        form.add("subjectId", "http://www.lemonde.fr");
        final ClientResource resource = restlet.newClientResource("/opinions");

        resource.post(form);

        assertThat(resource.getStatus().isError(), is(true));
        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void postOpinionRedirectOnFirstPage() {
        final WebPage webPage = TestFactories.subjects().newWebPage();
        final Form form = getGoodForm();
        form.add("subjectId", webPage.getId().toString());
        form.add("redirect", "webpages");
        final ClientResource opinionsResource = restlet.newClientResource("/opinions");

        opinionsResource.post(form);

        final String uriToRedirect = new ReferenceBuilder(Context.getCurrent()).buildUri("/webpages/" + webPage.getId());
        assertThat(opinionsResource.getLocationRef().toString(), is(uriToRedirect));
    }

    @Test
    public void canRedirectForConcept() {
        final Concept concept = TestFactories.subjects().newConcept();
        final Form form = getGoodForm();
        form.add("subjectId", concept.getId().toString());
        form.add("redirect", "concepts");
        final ClientResource opinionsResource = restlet.newClientResource("/opinions");

        opinionsResource.post(form);

        final String uriToRedirect = new ReferenceBuilder(Context.getCurrent()).buildUri("/concepts/" + concept.getId());
        assertThat(opinionsResource.getLocationRef().toString(), is(uriToRedirect));
    }

    private Form getGoodForm() {
        final Form form = new Form();
        form.add("text", "my opinion");
        form.add("feeling", "good");
        return form;
    }
}