package com.steambeat.web.resources.json;

import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.*;
import com.steambeat.web.*;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.restlet.data.*;
import org.restlet.representation.Representation;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsJsonIllustrationsResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void illustrationsResourceIsMapped() {
        final Reference reference = TestFactories.references().newReference();
        final ClientResource resource = restlet.newClientResource("/json/illustrations?referenceId=" + reference.getId());

        final Representation representation = resource.get();

        assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
        assertThat(representation.getMediaType(), is(MediaType.APPLICATION_JSON));
    }

    @Test
    public void canGetIllustrationsForAReference() throws IOException, JSONException {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.illustrations().newIllustration(reference, "link");
        final ClientResource resource = restlet.newClientResource("/json/illustrations?referenceId=" + reference.getId());

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(1));
    }

    @Test
    public void canGetIllustrationsForMultipleReferences() throws IOException, JSONException {
        final Reference ref1 = TestFactories.references().newReference();
        final Reference ref2 = TestFactories.references().newReference();
        final Reference ref3 = TestFactories.references().newReference();
        TestFactories.illustrations().newIllustration(ref1, "link");
        TestFactories.illustrations().newIllustration(ref2, "link");
        TestFactories.illustrations().newIllustration(ref3, "link");
        final ClientResource resource = restlet.newClientResource("/json/illustrations?referenceId=" + ref1.getId() + "," + ref2.getId());

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray, notNullValue());
        assertThat(jsonArray.length(), is(2));
    }
}
