package com.feelhub.web.resources.api;

import com.feelhub.domain.feeling.Feeling;
import com.feelhub.domain.topic.usable.real.RealTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;

import java.io.IOException;
import java.util.List;

import static org.fest.assertions.Assertions.*;


public class TestsApiTopicNewFeelingsResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void isMapped() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Feeling lastFeeling = TestFactories.feelings().newFeeling();
        final ClientResource newFeelings = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/feeling/" + lastFeeling.getId() + "/new");

        newFeelings.get();

        assertThat(newFeelings.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void returnAJsonArrayOfFeelings() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final List<Feeling> feelings = TestFactories.feelings().newFeelings(realTopic.getId(), 20);
        final Feeling lastFeeling = feelings.get(10);
        final ClientResource newFeelings = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/feeling/" + lastFeeling.getId() + "/new");

        final TemplateRepresentation representation = (TemplateRepresentation) newFeelings.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
    }

    @Test
    public void returnKnownFeelingsUntilLast() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final List<Feeling> feelings = TestFactories.feelings().newFeelings(realTopic.getId(), 4);
        final Feeling lastFeeling = feelings.get(2);
        final ClientResource newFeelings = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/feeling/" + lastFeeling.getId() + "/new");

        final TemplateRepresentation representation = (TemplateRepresentation) newFeelings.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length()).isEqualTo(2);
    }

    @Test
    public void returnKnownFeelingsUntilLastWithAVeryHighNumberOfNewFeelings() throws IOException, JSONException {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final List<Feeling> feelings = TestFactories.feelings().newFeelings(realTopic.getId(), 1000);
        final Feeling lastFeeling = feelings.get(900);
        final ClientResource newFeelings = restlet.newClientResource("/api/topic/" + realTopic.getId() + "/feeling/" + lastFeeling.getId() + "/new");

        final TemplateRepresentation representation = (TemplateRepresentation) newFeelings.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length()).isEqualTo(900);
    }
}
