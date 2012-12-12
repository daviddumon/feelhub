package com.feelhub.web.resources.api;

import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.feelhub.web.*;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.restlet.data.*;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;

import java.io.IOException;
import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsApiTopicIllustrationsResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void illustrationsResourceIsMapped() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/illustrations");

        final Representation representation = resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.SUCCESS_OK);
        assertThat(representation.getMediaType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    public void canGetIllustrationsForATopic() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.illustrations().newIllustration(realTopic.getId(), "link");
        final ClientResource resource = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/illustrations");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(1);
    }

    @Test
    public void canThrowException() {
        final ClientResource resource = restlet.newClientResource("/api/topic/" + UUID.randomUUID() + "/illustrations");

        final Representation representation = resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.CLIENT_ERROR_NOT_FOUND);
    }
}
