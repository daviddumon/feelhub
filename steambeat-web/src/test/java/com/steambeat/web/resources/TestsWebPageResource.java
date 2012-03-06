package com.steambeat.web.resources;

import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.web.*;
import org.junit.*;
import org.restlet.Context;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsWebPageResource {

    @Rule
    public final WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Test
    public void isMapped() throws IOException {
        final WebPage webPage = TestFactories.webPages().newWebPage();
        final ClientResource webpageResource = restlet.newClientResource("/webpages/" + webPage.getSemanticDescription() + "/" + webPage.getId());

        final Representation response = webpageResource.get();

        assertThat(webpageResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canRepresentNonExistingWebPage() {
        final ClientResource clientResource = restlet.newClientResource("/webpages/semantic/" + UUID.randomUUID().toString());

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) clientResource.get();

        assertThat(clientResource.getStatus(), is(Status.CLIENT_ERROR_NOT_FOUND));
    }

    @Test
    public void canRepresentExistingWebPage() {
        final WebPage webPage = TestFactories.webPages().newWebPage();
        final ClientResource webpageResource = restlet.newClientResource("/webpages/" + webPage.getSemanticDescription() + "/" + webPage.getId());

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) webpageResource.get();

        final Map<String, Object> dataModel = representation.getDataModel();
        assertThat(dataModel, hasEntry("webPage", (Object) webPage));
    }

    @Test
    public void permanentRedirectOnBadSemantic() {
        final WebPage webPage = TestFactories.webPages().newWebPage();
        final ClientResource webpageResource = restlet.newClientResource("/webpages/semantic/" + webPage.getId());

        webpageResource.get();

        assertThat(webpageResource.getStatus(), is(Status.REDIRECTION_PERMANENT));
        final String uriToRedirect = new ReferenceBuilder(Context.getCurrent()).buildUri("/webpages/" + webPage.getSemanticDescription() + "/" + webPage.getId());
        assertThat(webpageResource.getLocationRef().toString(), is(uriToRedirect));
    }
}
