package com.steambeat.web.resources;

import com.steambeat.domain.DomainEventBus;
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

public class TestHomeOpinionsResource {

    @Rule
    public WithDomainEvent withDomainEvent = new WithDomainEvent();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Test
    public void isMapped() {
        ClientResource resource = restlet.newClientResource("/opinions");
        
        resource.get();

        assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetAnOpinion() {
        ClientResource resource = restlet.newClientResource("/opinions");
        
        final JsonRepresentation representation = (JsonRepresentation) resource.get();

        assertThat(representation, notNullValue());
    }

    @Test
    public void canGetMultipleOpinions() throws JSONException {
        ClientResource resource = restlet.newClientResource("/opinions");
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
}
