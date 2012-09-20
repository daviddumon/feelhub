package com.steambeat.web.resources.json;

import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import com.steambeat.web.*;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.restlet.data.Status;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsRelatedResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void relatedResourceIsMapped() {
        final ClientResource relatedResource = restlet.newClientResource("/json/related");

        relatedResource.get();

        assertThat(relatedResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetWithQueryString() {
        final ClientResource relatedResource = restlet.newClientResource("/json/related?q=test");

        relatedResource.get();

        assertThat(relatedResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetARelation() throws IOException, JSONException {
        TestFactories.relations().newRelation();
        final ClientResource resource = restlet.newClientResource("/json/related?skip=0&limit=1");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(1));
    }

    @Test
    public void canGetRelationsForASubject() throws IOException, JSONException {
        final Reference from = TestFactories.references().newReference();
        TestFactories.relations().newRelations(5, from);
        TestFactories.relations().newRelations(20);
        final ClientResource resource = restlet.newClientResource("/json/related?fromId=" + from.getId());

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(5));
    }

    @Test
    public void canGetRelationsWithSkip() throws IOException, JSONException {
        TestFactories.relations().newRelations(5);
        final ClientResource resource = restlet.newClientResource("/json/related?skip=2");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(3));
    }

    @Test
    public void canGetRelationsWithLimit() throws IOException, JSONException {
        TestFactories.relations().newRelations(5);
        final ClientResource resource = restlet.newClientResource("/json/related?limit=2");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(2));
    }

    @Test
    public void defaultLimitIs100() throws IOException, JSONException {
        TestFactories.relations().newRelations(150);
        final ClientResource resource = restlet.newClientResource("/json/related");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(100));
    }
}
