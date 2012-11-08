package com.feelhub.web.resources.json;

import com.feelhub.domain.feeling.Feeling;
import com.feelhub.domain.reference.Reference;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
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

public class TestsJsonNewFeelingsResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void isMapped() {
        final Reference reference = TestFactories.references().newReference();
        final Feeling lastFeeling = TestFactories.feelings().newFeeling();
        final ClientResource newFeelings = restlet.newClientResource("/json/newfeelings?referenceId=" + reference.getId() + "&lastFeelingId=" + lastFeeling.getId());

        newFeelings.get();

        assertThat(newFeelings.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void returnAJsonArrayOfFeelings() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        final List<Feeling> feelings = TestFactories.feelings().newFeelings(reference, 20);
        final Feeling lastFeeling = feelings.get(10);
        final ClientResource newFeelings = restlet.newClientResource("/json/newfeelings?referenceId=" + reference.getId() + "&lastFeelingId=" + lastFeeling.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) newFeelings.get();

        MatcherAssert.assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
    }

    @Test
    public void returnKnownFeelingsUntilLast() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        final List<Feeling> feelings = TestFactories.feelings().newFeelings(reference, 4);
        final Feeling lastFeeling = feelings.get(2);
        final ClientResource newFeelings = restlet.newClientResource("/json/newfeelings?referenceId=" + reference.getId() + "&lastFeelingId=" + lastFeeling.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) newFeelings.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length(), is(2));
    }

    @Test
    public void returnKnownFeelingsUntilLastWithAVeryHighNumberOfNewFeelings() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        final List<Feeling> feelings = TestFactories.feelings().newFeelings(reference, 1000);
        final Feeling lastFeeling = feelings.get(900);
        final ClientResource newFeelings = restlet.newClientResource("/json/newfeelings?referenceId=" + reference.getId() + "&lastFeelingId=" + lastFeeling.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) newFeelings.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length(), is(900));
    }

    @Test
    public void ifNoLastFeelingIdReturnAllFeelings() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.feelings().newFeelings(reference, 100);
        final ClientResource newFeelings = restlet.newClientResource("/json/newfeelings?referenceId=" + reference.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) newFeelings.get();

        MatcherAssert.assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(100));
    }

    @Test
    public void returnLanguageCodeOfFeeling() throws IOException, JSONException {
        TestFactories.feelings().newFeeling();
        final ClientResource newFeelings = restlet.newClientResource("/json/newfeelings");

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
        final ClientResource newFeelings = restlet.newClientResource("/json/newfeelings");

        final TemplateRepresentation representation = (TemplateRepresentation) newFeelings.get();

        MatcherAssert.assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(1));
        assertThat(jsonArray.getJSONObject(0).get("userId").toString(), is(feeling.getUserId()));
    }
}
