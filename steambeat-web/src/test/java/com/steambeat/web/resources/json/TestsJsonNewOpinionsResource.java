package com.steambeat.web.resources.json;

import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import com.steambeat.web.ClientResource;
import com.steambeat.web.WebApplicationTester;
import org.hamcrest.MatcherAssert;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsJsonNewOpinionsResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void isMapped() {
        final Reference reference = TestFactories.references().newReference();
        final Opinion lastOpinion = TestFactories.opinions().newOpinion();
        final ClientResource newOpinions = restlet.newClientResource("/json/newopinions?referenceId=" + reference.getId() + "&lastOpinionId=" + lastOpinion.getId());

        newOpinions.get();

        assertThat(newOpinions.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void returnAJsonArrayOfOpinions() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        final List<Opinion> opinions = TestFactories.opinions().newOpinions(reference, 20);
        final Opinion lastOpinion = opinions.get(10);
        final ClientResource newOpinions = restlet.newClientResource("/json/newopinions?referenceId=" + reference.getId() + "&lastOpinionId=" + lastOpinion.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) newOpinions.get();

        MatcherAssert.assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
    }

    @Test
    public void returnKnownOpinionsUntilLast() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        final List<Opinion> opinions = TestFactories.opinions().newOpinions(reference, 4);
        final Opinion lastOpinion = opinions.get(2);
        final ClientResource newOpinions = restlet.newClientResource("/json/newopinions?referenceId=" + reference.getId() + "&lastOpinionId=" + lastOpinion.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) newOpinions.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length(), is(2));
    }

    @Test
    public void returnKnownOpinionsUntilLastWithAVeryHighNumberOfNewOpinions() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        final List<Opinion> opinions = TestFactories.opinions().newOpinions(reference, 1000);
        final Opinion lastOpinion = opinions.get(900);
        final ClientResource newOpinions = restlet.newClientResource("/json/newopinions?referenceId=" + reference.getId() + "&lastOpinionId=" + lastOpinion.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) newOpinions.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length(), is(900));
    }

    @Test
    public void ifNoLastOpinionIdReturnAllOpinions() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.opinions().newOpinions(reference, 100);
        final ClientResource newOpinions = restlet.newClientResource("/json/newopinions?referenceId=" + reference.getId());

        final TemplateRepresentation representation = (TemplateRepresentation) newOpinions.get();

        MatcherAssert.assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(100));
    }

    @Test
    public void returnLanguageCodeOfOpinion() throws IOException, JSONException {
        TestFactories.opinions().newOpinion();
        final ClientResource newOpinions = restlet.newClientResource("/json/newopinions");

        final TemplateRepresentation representation = (TemplateRepresentation) newOpinions.get();

        MatcherAssert.assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(1));
        assertThat(jsonArray.getJSONObject(0).get("languageCode").toString(), is(SteambeatLanguage.reference().getCode()));
    }


    @Test
    public void returnUserIdOfOpinion() throws IOException, JSONException {
        final Opinion opinion = TestFactories.opinions().newOpinion();
        final ClientResource newOpinions = restlet.newClientResource("/json/newopinions");

        final TemplateRepresentation representation = (TemplateRepresentation) newOpinions.get();

        MatcherAssert.assertThat(representation, notNullValue());
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(1));
        assertThat(jsonArray.getJSONObject(0).get("userId").toString(), is(opinion.getUserId()));
    }
}
