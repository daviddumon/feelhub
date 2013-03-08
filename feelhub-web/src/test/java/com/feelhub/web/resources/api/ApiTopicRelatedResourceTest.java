package com.feelhub.web.resources.api;

import com.feelhub.domain.related.Related;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.user.User;
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

public class ApiTopicRelatedResourceTest {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void before() {
        final User user = TestFactories.users().createFakeUser("mail@mail.com", "full name");
        CurrentUser.set(new WebUser(user, true));
        final Injector injector = Guice.createInjector(new GuiceTestModule());
        final ApiTopicRelatedResource apiTopicRelatedResource = injector.getInstance(ApiTopicRelatedResource.class);
        ContextTestFactory.initResource(apiTopicRelatedResource);
    }

    @Test
    public void apiTopicRelatedResourceIsMapped() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final ClientResource relatedResource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/related");

        relatedResource.get();

        assertThat(relatedResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void canGetWithQueryString() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final ClientResource relatedResource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/related?q=test");

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
        final Related related = TestFactories.related().newRelated();
        final ClientResource relatedResource = restlet.newClientResource("/api/topic/" + related.getFromId() + "/related?skip=0&limit=1");

        final Representation representation = relatedResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(1);
    }

    @Test
    public void canGetRelatedForATopic() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.related().newRelatedList(5, realTopic.getId());
        TestFactories.related().newRelatedList(20);
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/related");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(5);
    }

    @Test
    public void canGetRelatedWithSkip() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.related().newRelatedList(5, realTopic.getId());
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/related?skip=2");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(3);
    }

    @Test
    public void canGetRelatedWithLimit() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.related().newRelatedList(5, realTopic.getId());
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/related?limit=2");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(2);
    }

    @Test
    public void defaultLimitIs100() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.related().newRelatedList(150, realTopic.getId());
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/related");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(100);
    }

    @Test
    public void canGetEmptyTopicData() throws IOException, JSONException {
        final Related related = TestFactories.related().newRelated();
        final ClientResource resource = restlet.newClientResource("/api/topic/" + related.getFromId() + "/related");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject topicDataAsJson = jsonArray.getJSONObject(0);
        assertThat(topicDataAsJson).isNotNull();
        assertThat(topicDataAsJson.get("id").toString()).isEqualTo(related.getToId().toString());
        assertThat(topicDataAsJson.get("name").toString()).isEqualTo("Name-reference");
        assertThat(topicDataAsJson.get("thumbnailLarge").toString()).isEqualTo("thumbnailLarge");
        assertThat(topicDataAsJson.get("thumbnailMedium").toString()).isEqualTo("thumbnailMedium");
        assertThat(topicDataAsJson.get("thumbnailSmall").toString()).isEqualTo("thumbnailSmall");
    }

    @Test
    public void canGetWithGoodIllustration() throws IOException, JSONException {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();
        final Related related = TestFactories.related().newRelated(from.getId(), to.getId());
        final String illustration = "illustration";
        to.setThumbnailLarge(illustration);
        to.setThumbnailMedium(illustration);
        to.setThumbnailSmall(illustration);
        final ClientResource resource = restlet.newClientResource("/api/topic/" + related.getFromId() + "/related");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject topicDataAsJson = jsonArray.getJSONObject(0);
        assertThat(topicDataAsJson).isNotNull();
        assertThat(topicDataAsJson.get("id").toString()).isEqualTo(to.getId().toString());
        assertThat(topicDataAsJson.get("thumbnailLarge").toString()).isEqualTo(illustration);
        assertThat(topicDataAsJson.get("thumbnailMedium").toString()).isEqualTo(illustration);
        assertThat(topicDataAsJson.get("thumbnailSmall").toString()).isEqualTo(illustration);
    }

}
