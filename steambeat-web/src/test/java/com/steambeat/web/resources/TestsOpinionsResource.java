package com.steambeat.web.resources;

import com.google.common.collect.Lists;
import com.steambeat.domain.DomainEventBus;
import com.steambeat.domain.opinion.Feeling;
import com.steambeat.repositories.TestWithMongoRepository;
import com.steambeat.test.WithDomainEvent;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.web.*;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.restlet.data.*;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpinionsResource extends TestWithMongoRepository {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent events = new WithDomainEvent();

    @Test
    public void isMapped() {
        final ClientResource opinionsResource = restlet.newClientResource("/opinions");

        opinionsResource.get();

        assertThat(opinionsResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetWithQueryString() {
        final ClientResource opinionsResource = restlet.newClientResource("/opinions&q=test");

        opinionsResource.get();

        assertThat(opinionsResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Ignore
    @Test
    public void canGetAnOpinion() throws IOException, JSONException {
        TestFactories.opinions().newOpinion();
        final ClientResource resource = restlet.newClientResource("/opinions?skip=0&limit=1");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(1));
    }

    @Ignore
    @Test
    public void canGetMultipleOpinions() throws JSONException, IOException {
        final ClientResource resource = restlet.newClientResource("/opinions?skip=0&limit=10");
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();
        DomainEventBus.INSTANCE.flush();

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(3));
    }

    @Ignore
    @Test
    public void canGetMultipleOpinionsWithSkip() throws JSONException, IOException {
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();
        DomainEventBus.INSTANCE.flush();
        final ClientResource resource = restlet.newClientResource("/opinions?skip=1");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(2));
    }

    @Ignore("Restlet framework catch it in UniformResource ...")
    @Test
    public void canThrowSteambeatJsonException() {
        exception.expect(SteambeatJsonException.class);

        final ClientResource resource = restlet.newClientResource("/opinions;0;101");

        resource.get();
    }

    @Ignore
    @Test
    public void canPostAnOpinion() {
        final Form form = getGoodForm();
        final ClientResource clientResource = restlet.newClientResource("/opinions");

        clientResource.post(form);

        assertThat(clientResource.getStatus(), is(Status.SUCCESS_CREATED));
        //assertThat(Repositories.opinions().getAll().size(), is(1));
    }

    private Form getGoodForm() {
        final Form form = new Form();
        form.add("text", "my opinion");
        final List<Parameter> judgments = Lists.newArrayList();
        judgments.add(new Parameter("http://www.fake.com", Feeling.good.toString()));
        form.addAll(judgments);
        return form;
    }
}
