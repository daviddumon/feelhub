package com.steambeat.web.resources;

import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.web.*;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.restlet.data.Status;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsRelationsResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void isMapped() {
        final ClientResource relationsResource = restlet.newClientResource("/relations");

        relationsResource.get();

        assertThat(relationsResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetWithQueryString() {
        final ClientResource relationsResource = restlet.newClientResource("/relations?q=test");

        relationsResource.get();

        assertThat(relationsResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetARelation() throws IOException, JSONException {
        TestFactories.relations().newRelation();
        final ClientResource resource = restlet.newClientResource("/relations?skip=0&limit=1");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(1));
    }

    @Test
    public void canGetRelationsForASubject() throws IOException, JSONException {
        final WebPage from = TestFactories.subjects().newWebPage();
        TestFactories.relations().newRelations(5, from);
        TestFactories.relations().newRelations(20);
        final ClientResource resource = restlet.newClientResource("/relations?fromId=" + from.getId());

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(5));
    }

    @Test
    public void canGetRelationsWithSkip() throws IOException, JSONException {
        TestFactories.relations().newRelations(5);
        final ClientResource resource = restlet.newClientResource("/relations?skip=2");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(3));
    }

    @Test
    public void canGetRelationsWithLimit() throws IOException, JSONException {
        TestFactories.relations().newRelations(5);
        final ClientResource resource = restlet.newClientResource("/relations?limit=2");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(2));
    }

    @Test
    public void defaultLimitIs100() throws IOException, JSONException {
        TestFactories.relations().newRelations(150);
        final ClientResource resource = restlet.newClientResource("/relations");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(100));
    }
}
