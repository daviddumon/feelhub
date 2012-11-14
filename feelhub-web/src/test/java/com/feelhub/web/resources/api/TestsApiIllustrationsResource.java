package com.feelhub.web.resources.api;

import com.feelhub.domain.topic.Topic;
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

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsApiIllustrationsResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void illustrationsResourceIsMapped() {
        final Topic topic = TestFactories.topics().newTopic();
        final ClientResource resource = restlet.newClientResource("/api/illustrations?topicId=" + topic.getId());

        final Representation representation = resource.get();

        assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
        assertThat(representation.getMediaType(), is(MediaType.APPLICATION_JSON));
    }

    @Test
    public void canGetIllustrationsForATopic() throws IOException, JSONException {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.illustrations().newIllustration(topic, "link");
        final ClientResource resource = restlet.newClientResource("/api/illustrations?topicId=" + topic.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(1));
    }

    @Test
    public void canGetIllustrationsForMultipleTopics() throws IOException, JSONException {
        final Topic topic1 = TestFactories.topics().newTopic();
        final Topic topic2 = TestFactories.topics().newTopic();
        final Topic topic3 = TestFactories.topics().newTopic();
        TestFactories.illustrations().newIllustration(topic1, "link");
        TestFactories.illustrations().newIllustration(topic2, "link");
        TestFactories.illustrations().newIllustration(topic3, "link");
        final ClientResource resource = restlet.newClientResource("/api/illustrations?topicId=" + topic1.getId() + "," + topic2.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(2));
    }
}
