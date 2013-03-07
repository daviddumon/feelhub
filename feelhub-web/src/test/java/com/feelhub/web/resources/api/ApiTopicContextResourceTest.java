package com.feelhub.web.resources.api;

import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.test.TestFactories;
import com.feelhub.web.ClientResource;
import com.feelhub.web.WebApplicationTester;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.restlet.representation.Representation;

import java.io.IOException;

import static org.fest.assertions.Assertions.*;

public class ApiTopicContextResourceTest {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void returnArrayContainingContext() throws IOException, JSONException {
        final RealTopic topic1 = TestFactories.topics().newCompleteRealTopic("name1");
        final RealTopic topic2 = TestFactories.topics().newCompleteRealTopic("name2");
        final RealTopic topic3 = TestFactories.topics().newCompleteRealTopic("name3");
        TestFactories.tags().newTag("value1", topic2);
        TestFactories.tags().newTag("value2", topic2);
        TestFactories.tags().newTag("value3", topic3);
        TestFactories.related().newRelated(topic1.getId(), topic2.getId());
        TestFactories.related().newRelated(topic1.getId(), topic3.getId());
        final ClientResource resource = restlet.newClientResource("/api/topic/" + topic1.getCurrentId() + "/context");

        final Representation representation = resource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(3);
    }

    private ApiTopicContextResource apiTopicContextResource;
}
