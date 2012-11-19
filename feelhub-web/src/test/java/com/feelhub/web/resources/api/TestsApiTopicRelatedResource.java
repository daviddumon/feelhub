package com.feelhub.web.resources.api;

import com.feelhub.domain.meta.Illustration;
import com.feelhub.domain.relation.Relation;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.authentification.*;
import com.feelhub.web.guice.GuiceTestModule;
import com.google.inject.*;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

import java.io.IOException;
import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsApiTopicRelatedResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        user = TestFactories.users().createFakeUser("mail@mail.com", "full name");
        CurrentUser.set(new WebUser(user, true));
        injector = Guice.createInjector(new GuiceTestModule());
        apiTopicRelatedResource = injector.getInstance(ApiTopicRelatedResource.class);
        ContextTestFactory.initResource(apiTopicRelatedResource);
    }

    @Test
    public void apiTopicRelatedResourceIsMapped() {
        final Topic topic = TestFactories.topics().newTopic();
        final ClientResource relatedResource = restlet.newClientResource("/api/topic/" + topic.getId() + "/related");

        relatedResource.get();

        assertThat(relatedResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void canGetWithQueryString() {
        final Topic topic = TestFactories.topics().newTopic();
        final ClientResource relatedResource = restlet.newClientResource("/api/topic/" + topic.getId() + "/related?q=test");

        relatedResource.get();

        assertThat(relatedResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void canThrowError() {
        final ClientResource relatedResource = restlet.newClientResource("/api/topic/" + UUID.randomUUID() + "/related?q=test");

        relatedResource.get();

        assertThat(relatedResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void canGetARelated() throws IOException, JSONException {
        final Relation relation = TestFactories.relations().newRelation();
        final ClientResource relatedResource = restlet.newClientResource("/api/topic/" + relation.getFromId() + "/related?skip=0&limit=1");

        final Representation representation = relatedResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(1);
    }

    @Test
    public void canGetRelatedForATopic() throws IOException, JSONException {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.relations().newRelations(5, topic.getId());
        TestFactories.relations().newRelations(20);
        final ClientResource resource = restlet.newClientResource("/api/topic/" + topic.getId() + "/related");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(5);
    }

    @Test
    public void canGetRelatedWithSkip() throws IOException, JSONException {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.relations().newRelations(5, topic.getId());
        final ClientResource resource = restlet.newClientResource("/api/topic/" + topic.getId() + "/related?skip=2");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(3);
    }

    @Test
    public void canGetRelatedWithLimit() throws IOException, JSONException {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.relations().newRelations(5, topic.getId());
        final ClientResource resource = restlet.newClientResource("/api/topic/" + topic.getId() + "/related?limit=2");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(2);
    }

    @Test
    public void defaultLimitIs100() throws IOException, JSONException {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.relations().newRelations(150, topic.getId());
        final ClientResource resource = restlet.newClientResource("/api/topic/" + topic.getId() + "/related");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(100);
    }

    @Test
    public void canGetEmptyKeywordData() throws IOException, JSONException {
        final Relation relation = TestFactories.relations().newRelation();
        final ClientResource resource = restlet.newClientResource("/api/topic/" + relation.getFromId() + "/related");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject keywordDataAsJson = jsonArray.getJSONObject(0);
        assertThat(keywordDataAsJson).isNotNull();
        assertThat(keywordDataAsJson.get("id").toString()).isEqualTo(relation.getToId().toString());
        assertThat(keywordDataAsJson.get("description").toString()).isEqualTo("description-reference");
        assertThat(keywordDataAsJson.get("illustrationLink").toString()).isEmpty();
    }

    @Test
    public void canGetWithGoodIllustration() throws IOException, JSONException {
        final Topic from = TestFactories.topics().newTopic();
        final Topic to = TestFactories.topics().newTopic();
        final Relation relation = TestFactories.relations().newRelation(from.getId(), to.getId());
        final Illustration illustration = TestFactories.illustrations().newIllustration(to.getId());
        final ClientResource resource = restlet.newClientResource("/api/topic/" + relation.getFromId() + "/related");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject keywordDataAsJson = jsonArray.getJSONObject(0);
        assertThat(keywordDataAsJson).isNotNull();
        assertThat(keywordDataAsJson.get("id").toString()).isEqualTo(to.getId().toString());
        assertThat(keywordDataAsJson.get("description").toString()).isEqualTo("description-reference");
        assertThat(keywordDataAsJson.get("illustrationLink").toString()).isEqualTo(illustration.getLink());
    }

    private User user;
    private Injector injector;
    private ApiTopicRelatedResource apiTopicRelatedResource;
}