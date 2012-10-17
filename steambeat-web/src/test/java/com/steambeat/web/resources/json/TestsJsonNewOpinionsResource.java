package com.steambeat.web.resources.json;

import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import com.steambeat.web.*;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.hamcrest.MatcherAssert;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.restlet.data.Status;

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

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) newOpinions.get();

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

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) newOpinions.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length(), is(2));
    }

    @Test
    public void errorIfMissingLastOpinionParameter() {
        final Reference reference = TestFactories.references().newReference();
        final ClientResource newOpinions = restlet.newClientResource("/json/newopinions?referenceId=" + reference.getId());

        newOpinions.get();

        assertThat(newOpinions.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void errorIfMissingReferenceParameter() {
        final Opinion lastOpinion = TestFactories.opinions().newOpinion();
        final ClientResource newOpinions = restlet.newClientResource("/json/newopinions?lastOpinionId=" + lastOpinion.getId());

        newOpinions.get();

        assertThat(newOpinions.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void returnKnownOpinionsUntilLastWithAVeryHighNumberOfNewOpinions() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        final List<Opinion> opinions = TestFactories.opinions().newOpinions(reference, 1000);
        final Opinion lastOpinion = opinions.get(900);
        final ClientResource newOpinions = restlet.newClientResource("/json/newopinions?referenceId=" + reference.getId() + "&lastOpinionId=" + lastOpinion.getId());

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) newOpinions.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray.length(), is(900));
    }
}
