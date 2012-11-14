package com.feelhub.web.resources.api;

import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsApiRelationsResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void relationsResourceIsMapped() {
        final ClientResource relationsResource = restlet.newClientResource("/api/relations");

        relationsResource.get();

        assertThat(relationsResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetWithQueryString() {
        final ClientResource relationsResource = restlet.newClientResource("/api/relations?q=test");

        relationsResource.get();

        assertThat(relationsResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetARelation() throws IOException, JSONException {
        TestFactories.relations().newRelation();
        final ClientResource resource = restlet.newClientResource("/api/relations?skip=0&limit=1");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(1));
    }

    @Test
    public void canGetRelationsForATopic() throws IOException, JSONException {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.relations().newRelations(5, topic);
        TestFactories.relations().newRelations(20);
        final ClientResource resource = restlet.newClientResource("/api/relations?topicId=" + topic.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(5));
    }

    @Test
    public void canGetRelationsWithSkip() throws IOException, JSONException {
        TestFactories.relations().newRelations(5);
        final ClientResource resource = restlet.newClientResource("/api/relations?skip=2");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(3));
    }

    @Test
    public void canGetRelationsWithLimit() throws IOException, JSONException {
        TestFactories.relations().newRelations(5);
        final ClientResource resource = restlet.newClientResource("/api/relations?limit=2");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(2));
    }

    @Test
    public void defaultLimitIs100() throws IOException, JSONException {
        TestFactories.relations().newRelations(150);
        final ClientResource resource = restlet.newClientResource("/api/relations");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(100));
    }
}
