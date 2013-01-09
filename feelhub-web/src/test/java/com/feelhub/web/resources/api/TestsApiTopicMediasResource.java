package com.feelhub.web.resources.api;

import com.feelhub.domain.relation.Relation;
import com.feelhub.domain.topic.real.RealTopic;
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

public class TestsApiTopicMediasResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        user = TestFactories.users().createFakeUser("mail@mail.com", "full name");
        CurrentUser.set(new WebUser(user, true));
        injector = Guice.createInjector(new GuiceTestModule());
        apiTopicMediasResource = injector.getInstance(ApiTopicMediasResource.class);
        ContextTestFactory.initResource(apiTopicMediasResource);
    }

    @Test
    public void apiTopicMediasResourceIsMapped() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final ClientResource mediasResource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/medias");

        mediasResource.get();

        assertThat(mediasResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void canGetWithQueryString() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final ClientResource mediasResource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/medias?q=test");

        mediasResource.get();

        assertThat(mediasResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void canThrowError() {
        final ClientResource mediasResource = restlet.newClientResource("/api/topic/" + UUID.randomUUID() + "/medias?q=test");

        mediasResource.get();

        assertThat(mediasResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void canGetAMedia() throws IOException, JSONException {
        final Relation relation = TestFactories.relations().newMedia();
        final ClientResource mediasResource = restlet.newClientResource("/api/topic/" + relation.getFromId() + "/medias?skip=0&limit=1");

        final Representation representation = mediasResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(1);
    }

    @Test
    public void canGetMediasForATopic() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newMediaList(5, realTopic.getId());
        TestFactories.relations().newMediaList(20);
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/medias");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(5);
    }

    @Test
    public void canGetMediasWithSkip() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newMediaList(5, realTopic.getId());
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/medias?skip=2");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(3);
    }

    @Test
    public void canGetMediasWithLimit() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newMediaList(5, realTopic.getId());
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/medias?limit=2");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(2);
    }

    @Test
    public void defaultLimitIs100() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newMediaList(150, realTopic.getId());
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/medias");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(100);
    }

    @Test
    public void canGetEmptyTopicData() throws IOException, JSONException {
        final Relation relation = TestFactories.relations().newMedia();
        final ClientResource resource = restlet.newClientResource("/api/topic/" + relation.getFromId() + "/medias");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject keywordDataAsJson = jsonArray.getJSONObject(0);
        assertThat(keywordDataAsJson).isNotNull();
        assertThat(keywordDataAsJson.get("id").toString()).isEqualTo(relation.getToId().toString());
        assertThat(keywordDataAsJson.get("name").toString()).isEqualTo("Name-reference");
        assertThat(keywordDataAsJson.get("illustration").toString()).isEmpty();
    }

    @Test
    public void canGetWithGoodIllustration() throws IOException, JSONException {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();
        final Relation relation = TestFactories.relations().newRelated(from.getId(), to.getId());
        final String illustration = "illustration";
        to.setIllustration(illustration);
        final ClientResource resource = restlet.newClientResource("/api/topic/" + relation.getFromId() + "/related");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject keywordDataAsJson = jsonArray.getJSONObject(0);
        assertThat(keywordDataAsJson).isNotNull();
        assertThat(keywordDataAsJson.get("id").toString()).isEqualTo(to.getId().toString());
        assertThat(keywordDataAsJson.get("illustration").toString()).isEqualTo(illustration);
    }

    private User user;
    private Injector injector;
    private ApiTopicMediasResource apiTopicMediasResource;
}
