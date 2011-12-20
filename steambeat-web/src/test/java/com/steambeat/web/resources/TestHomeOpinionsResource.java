package com.steambeat.web.resources;

import com.steambeat.domain.DomainEventBus;
import com.steambeat.test.WithDomainEvent;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.web.ClientResource;
import com.steambeat.web.SteambeatTemplateRepresentation;
import com.steambeat.web.WebApplicationTester;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.restlet.data.Status;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestHomeOpinionsResource {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public WithDomainEvent withDomainEvent = new WithDomainEvent();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Test
    public void isMapped() {
        final ClientResource resource = restlet.newClientResource("/opinions;10;10");

        resource.get();

        assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetAnOpinion() {
        final ClientResource resource = restlet.newClientResource("/opinions;10;10");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
    }

    @Test
    public void canGetMultipleOpinions() throws JSONException, IOException {
        final ClientResource resource = restlet.newClientResource("/opinions;0;10");
        create3Opinions();

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();


        JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(3));
    }

    @Ignore
    @Test
    public void canThrowException() {
        exception.expect(SteambeatJsonException.class);

        final ClientResource resource = restlet.newClientResource("/opinions;0;200");

        resource.get();
    }

    private void create3Opinions() {
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();
        DomainEventBus.INSTANCE.flush();
    }
}
