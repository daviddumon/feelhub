package com.steambeat.web.resources.json;

import com.steambeat.domain.eventbus.WithDomainEvent;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.topic.Topic;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.TestFactories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
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

    @Test
    public void opinionsResourceIsMapped() {
        final ClientResource opinionsResource = restlet.newClientResource("/json/opinions");

        opinionsResource.get();

        assertThat(opinionsResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetWithQueryString() {
        final ClientResource opinionsResource = restlet.newClientResource("/json/opinions?q=test");

        opinionsResource.get();

        assertThat(opinionsResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetAnOpinion() throws IOException, JSONException {
        TestFactories.opinions().newOpinion();
        final ClientResource resource = restlet.newClientResource("/json/opinions?skip=0&limit=1");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(1));
    }

    @Test
    public void defaultLimitIs100() throws IOException, JSONException {
        TestFactories.opinions().newOpinions(150);
        final ClientResource clientResource = restlet.newClientResource("/json/opinions?skip=0");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) clientResource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length(), is(100));
    }

    @Test
    public void defaultSkipIs0() throws IOException, JSONException {
        TestFactories.opinions().newOpinions(100);
        final ClientResource clientResource = restlet.newClientResource("/json/opinions?limit=10");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) clientResource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(((JSONObject) jsonArray.get(0)).get("text").toString(), is("i0"));
    }

    @Test
    public void canGetMultipleOpinions() throws JSONException, IOException {
        final ClientResource resource = restlet.newClientResource("/json/opinions?skip=0&limit=10");
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();

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
        final ClientResource resource = restlet.newClientResource("/json/opinions?skip=1");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(2));
    }

    @Test
    public void canThrowSteambeatJsonException() {
        final ClientResource resource = restlet.newClientResource("/json/opinions?skip=0&limit=101");

        resource.get();

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void canGetOpinionForSubject() throws IOException, JSONException {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.opinions().newOpinions(10);
        TestFactories.opinions().newOpinions(topic, 10);
        TestFactories.opinions().newOpinions(10);
        final ClientResource clientResource = restlet.newClientResource("/json/opinions?topicId=" + topic.getId());

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) clientResource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length(), is(10));
    }

    @Test
    public void canPostOpinion() {
        final Topic topic = TestFactories.topics().newTopic();
        final Form form = getGoodForm();
        form.add("topicId", topic.getId().toString());
        form.add("redirect", "webpages");
        final ClientResource opinionsResource = restlet.newClientResource("/json/opinions");

        opinionsResource.post(form);

        assertThat(opinionsResource.getStatus(), is(Status.SUCCESS_CREATED));
        assertThat(Repositories.opinions().getAll().size(), is(1));
        final Opinion opinion = Repositories.opinions().getAll().get(0);
        final Judgment judgment = opinion.getJudgments().get(0);
        assertThat(judgment.getFeeling(), is(Feeling.good));
        assertThat(judgment.getTopic(), is(topic));
    }

    @Test
    public void canPostOpinionForATopic() {
        final Topic topic = TestFactories.topics().newTopic();
        final Form form = getGoodForm();
        form.add("topicId", topic.getId().toString());
        form.add("redirect", "webpages");
        final ClientResource opinionsResource = restlet.newClientResource("/json/opinions");

        opinionsResource.post(form);

        assertThat(opinionsResource.getStatus(), is(Status.SUCCESS_CREATED));
        assertThat(Repositories.opinions().getAll().size(), is(1));
        final Opinion opinion = Repositories.opinions().getAll().get(0);
        final Judgment judgment = opinion.getJudgments().get(0);
        assertThat(judgment.getFeeling(), is(Feeling.good));
        assertThat(judgment.getTopic(), is(topic));
    }

    @Test
    public void throwExceptionWhenUnknownTopic() {
        final Form form = getGoodForm();
        form.add("topicId", UUID.randomUUID().toString());
        final ClientResource resource = restlet.newClientResource("/json/opinions");

        resource.post(form);

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void canNotPostAOpinionWithoutFeeling() {
        final Topic topic = TestFactories.topics().newTopic();
        final Form form = new Form();
        form.add("text", "my opinion");
        form.add("topicId", "http://www.lemonde.fr");
        final ClientResource resource = restlet.newClientResource("/json/opinions");

        resource.post(form);

        assertThat(resource.getStatus().isError(), is(true));
        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void postOpinionRedirectOnFirstPage() {
        final Topic topic = TestFactories.topics().newTopic();
        final Form form = getGoodForm();
        form.add("topicId", topic.getId().toString());
        form.add("redirect", "webpages");
        final ClientResource opinionsResource = restlet.newClientResource("/json/opinions");

        opinionsResource.post(form);

        final String uriToRedirect = new ReferenceBuilder(Context.getCurrent()).buildUri("/webpages/" + topic.getId());
        assertThat(opinionsResource.getLocationRef().toString(), is(uriToRedirect));
    }

    @Test
    public void canRedirectForTopic() {
        final Topic topic = TestFactories.topics().newTopic();
        final Form form = getGoodForm();
        form.add("topicId", topic.getId().toString());
        form.add("redirect", "topics");
        final ClientResource opinionsResource = restlet.newClientResource("/json/opinions");

        opinionsResource.post(form);

        final String uriToRedirect = new ReferenceBuilder(Context.getCurrent()).buildUri("/topics/" + topic.getId());
        assertThat(opinionsResource.getLocationRef().toString(), is(uriToRedirect));
    }

    private Form getGoodForm() {
        final Form form = new Form();
        form.add("text", "my opinion");
        form.add("feeling", "good");
        return form;
    }
}
