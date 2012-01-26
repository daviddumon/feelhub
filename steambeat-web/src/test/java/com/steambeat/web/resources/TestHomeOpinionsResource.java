package com.steambeat.web.resources;

import com.steambeat.domain.*;
import com.steambeat.test.*;
import com.steambeat.test.fakeRepositories.*;
import com.steambeat.test.testFactories.*;
import com.steambeat.web.*;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.junit.rules.*;
import org.restlet.data.*;

import java.io.*;

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

    @Test
    public void canThrowException() {
        exception.expect(SteambeatJsonException.class);

        final ClientResource resource = restlet.newClientResource("/opinions;0;101");

        resource.get();
    }

    private void create3Opinions() {
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();
        TestFactories.opinions().newOpinion();
        DomainEventBus.INSTANCE.flush();
    }
}
