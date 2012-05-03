package com.steambeat.web.resources;

import com.steambeat.domain.subject.concept.Concept;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.web.*;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.junit.*;
import org.restlet.data.Status;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsConceptResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void isMapped() {
        final Concept concept = TestFactories.subjects().newConcept();
        final ClientResource conceptResource = restlet.newClientResource("/concepts/" + concept.getId());

        conceptResource.get();

        assertThat(conceptResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canRepresentNonExistingConcept() {
        final ClientResource clientResource = restlet.newClientResource("/concepts/" + UUID.randomUUID().toString());

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) clientResource.get();

        assertThat(clientResource.getStatus(), is(Status.CLIENT_ERROR_NOT_FOUND));
    }

    @Test
    public void canRepresentExistingConcept() {
        final Concept concept = TestFactories.subjects().newConcept();
        final ClientResource webpageResource = restlet.newClientResource("/concepts/" + concept.getId());

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) webpageResource.get();

        final Map<String, Object> dataModel = representation.getDataModel();
        assertThat(dataModel, hasEntry("concept", (Object) concept));
    }
}
