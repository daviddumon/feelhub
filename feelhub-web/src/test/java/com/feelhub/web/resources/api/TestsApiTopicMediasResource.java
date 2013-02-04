package com.feelhub.web.resources.api;

import com.feelhub.domain.media.Media;
import com.feelhub.domain.related.Related;
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
        final Media media = TestFactories.medias().newMedia();
        final ClientResource mediasResource = restlet.newClientResource("/api/topic/" + media.getFromId() + "/medias?skip=0&limit=1");

        final Representation representation = mediasResource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(1);
    }

    @Test
    public void canGetMediasForATopic() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.medias().newMediaList(5, realTopic.getId());
        TestFactories.medias().newMediaList(20);
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
        TestFactories.medias().newMediaList(5, realTopic.getId());
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
        TestFactories.medias().newMediaList(5, realTopic.getId());
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
        TestFactories.medias().newMediaList(150, realTopic.getId());
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/medias");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(100);
    }

    @Test
    public void canGetEmptyTopicData() throws IOException, JSONException {
        final Media media = TestFactories.medias().newMedia();
        final ClientResource resource = restlet.newClientResource("/api/topic/" + media.getFromId() + "/medias");

        final Representation representation = resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        final JSONObject keywordDataAsJson = jsonArray.getJSONObject(0);
        assertThat(keywordDataAsJson).isNotNull();
        assertThat(keywordDataAsJson.get("id").toString()).isEqualTo(media.getToId().toString());
        assertThat(keywordDataAsJson.get("name").toString()).isEqualTo("Name-reference");
        assertThat(keywordDataAsJson.get("thumbnailLarge").toString()).isEqualTo("thumbnailLarge");
        assertThat(keywordDataAsJson.get("thumbnailMedium").toString()).isEqualTo("thumbnailMedium");
        assertThat(keywordDataAsJson.get("thumbnailSmall").toString()).isEqualTo("thumbnailSmall");
    }

    @Test
    public void canGetWithGoodIllustration() throws IOException, JSONException {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();
        final Related related = TestFactories.related().newRelated(from.getId(), to.getId());
        final String illustration = "illustration";
        to.setThumbnailSmall(illustration);
        to.setThumbnailMedium(illustration);
        to.setThumbnailLarge(illustration);
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

    private User user;
    private Injector injector;
    private ApiTopicMediasResource apiTopicMediasResource;
}
