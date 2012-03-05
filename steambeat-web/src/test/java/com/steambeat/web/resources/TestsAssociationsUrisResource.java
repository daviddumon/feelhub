package com.steambeat.web.resources;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.web.*;
import org.junit.*;
import org.restlet.data.Status;
import org.restlet.representation.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsAssociationsUrisResource {

    @Rule
    public final WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canGet() {
        final Uri uri = new Uri("http://www.steambeat.com");
        final Association association = TestFactories.associations().newAssociation(uri);
        final ClientResource associationResource = restlet.newClientResource("/associations/uris?uri=" + association.getId());

        final StringRepresentation representation = (StringRepresentation) associationResource.get();

        assertThat(associationResource.getStatus(), is(Status.SUCCESS_OK));
        assertThat(representation.getText(), is(association.getSubjectId().toString()));
    }

    @Test
    public void canThrowBadRequestErrorIfNoUri() {
        final ClientResource associationResource = restlet.newClientResource("/associations/uris");

        final Representation representation = associationResource.get();

        assertThat(associationResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Ignore
    @Test
    public void canThrow404ErrorOnUnknownAssociation() {
        final String uri = "http://test.com?param=tonpere";
        final ClientResource associationResource = restlet.newClientResource("/associations/uris?uri=" + uri);

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) associationResource.get();

        assertThat(associationResource.getStatus(), is(Status.CLIENT_ERROR_NOT_FOUND));
    }
}
