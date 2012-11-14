package com.feelhub.web.resources.api;

import com.feelhub.domain.feeling.Feeling;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import org.hamcrest.MatcherAssert;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsApiNewFeelingsResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void isMapped() {
        final Topic topic = TestFactories.topics().newTopic();
        final Feeling lastFeeling = TestFactories.feelings().newFeeling();
        final ClientResource newFeelings = restlet.newClientResource("/api/newfeelings?topicId=" + topic.getId() + "&lastFeelingId=" + lastFeeling.getId());

        newFeelings.get();

        assertThat(newFeelings.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void returnAJsonArrayOfFeelings() throws IOException, JSONException {
        final Topic topic = TestFactories.topics().newTopic();
        final List<Feeling> feelings = TestFactories.feelings().newFeelings(topic, 20);
        final Feeling lastFeeling = feelings.get(10);
        final ClientResource newFeelings = restlet.newClientResource("/api/newfeelings?topicId=" + topic.getId() + "&lastFeelingId=" + lastFeeling.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) newFeelings.get();

        MatcherAssert.assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
    }

    @Test
    public void returnKnownFeelingsUntilLast() throws IOException, JSONException {
        final Topic topic = TestFactories.topics().newTopic();
        final List<Feeling> feelings = TestFactories.feelings().newFeelings(topic, 4);
        final Feeling lastFeeling = feelings.get(2);
        final ClientResource newFeelings = restlet.newClientResource("/api/newfeelings?topicId=" + topic.getId() + "&lastFeelingId=" + lastFeeling.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) newFeelings.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length(), is(2));
    }

    @Test
    public void returnKnownFeelingsUntilLastWithAVeryHighNumberOfNewFeelings() throws IOException, JSONException {
        final Topic topic = TestFactories.topics().newTopic();
        final List<Feeling> feelings = TestFactories.feelings().newFeelings(topic, 1000);
        final Feeling lastFeeling = feelings.get(900);
        final ClientResource newFeelings = restlet.newClientResource("/api/newfeelings?topicId=" + topic.getId() + "&lastFeelingId=" + lastFeeling.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) newFeelings.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length(), is(900));
    }

    @Test
    public void ifNoLastFeelingIdReturnAllFeelings() throws IOException, JSONException {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.feelings().newFeelings(topic, 100);
        final ClientResource newFeelings = restlet.newClientResource("/api/newfeelings?topicId=" + topic.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) newFeelings.get();

        MatcherAssert.assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(100));
    }

    @Test
    public void returnLanguageCodeOfFeeling() throws IOException, JSONException {
        TestFactories.feelings().newFeeling();
        final ClientResource newFeelings = restlet.newClientResource("/api/newfeelings");

        final TemplateRepresentation representation = (TemplateRepresentation) newFeelings.get();

        MatcherAssert.assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(1));
        assertThat(jsonArray.getJSONObject(0).get("languageCode").toString(), is(FeelhubLanguage.reference().getCode()));
    }


    @Test
    public void returnUserIdOfFeeling() throws IOException, JSONException {
        final Feeling feeling = TestFactories.feelings().newFeeling();
        final ClientResource newFeelings = restlet.newClientResource("/api/newfeelings");

        final TemplateRepresentation representation = (TemplateRepresentation) newFeelings.get();

        MatcherAssert.assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(1));
        assertThat(jsonArray.getJSONObject(0).get("userId").toString(), is(feeling.getUserId()));
    }
}
