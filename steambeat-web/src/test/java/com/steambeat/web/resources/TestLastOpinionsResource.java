package com.steambeat.web.resources;

import com.steambeat.domain.DomainEventBus;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.WithDomainEvent;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.web.*;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestLastOpinionsResource {

    @Rule
    public WithDomainEvent withDomainEvent = new WithDomainEvent();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Before
    public void setUp() throws Exception {
        resource = restlet.newClientResource("/lastopinions");
    }

    @Test
    public void isMapped() {
        resource.get();

        assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetAnLastOpinionLink() {
        final WebPage webPage = TestFactories.webPages().newWebPage("http://www.fake.com");

        final JsonRepresentation representation = (JsonRepresentation) resource.get();

        assertThat(representation, notNullValue());
    }

    @Test
    public void canGetAlllastOpinionLinks() throws JSONException {
        create3Opinions();

        final JsonRepresentation representation = (JsonRepresentation) resource.get();

        final JSONArray jsonArray = representation.getJsonArray();
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(3));
    }

    private void create3Opinions() {
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();
        DomainEventBus.INSTANCE.flush();
    }

    private ClientResource resource;
}
