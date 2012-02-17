package com.steambeat.web.resources;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.FakeUriScraper;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.web.*;
import org.junit.*;
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
        final WebPage webPage = new WebPage(new Association(new Uri("http://www.google.fr"), UUID.randomUUID()));
        final FakeUriScraper fakeUriScraper = new FakeUriScraper(Uri.empty());
        fakeUriScraper.scrap();
        webPage.update(fakeUriScraper);
        Repositories.webPages().add(webPage);
        final ClientResource resource = restlet.newClientResource("/webpages/http%3A%2F%2Fwww.google.fr");

        final Representation response = resource.get();

        assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canDealWithHierarchicalUri() throws IOException {
        final WebPage webPage = new WebPage(new Association(new Uri("http://www.slate.fr/story/36777/avenir-home-entertainment"), UUID.randomUUID()));
        final FakeUriScraper fakeUriScraper = new FakeUriScraper(Uri.empty());
        fakeUriScraper.scrap();
        webPage.update(fakeUriScraper);
        Repositories.webPages().add(webPage);
        final ClientResource resource = restlet.newClientResource("/webpages/http://www.slate.fr/story/36777/avenir-home-entertainment");

        final Representation representation = resource.get();

        assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canDealWithRedirection() {
        final WebPage webPage = new WebPage(new Association(new Uri("http://www.slate.fr/story/36777/avenir-home-entertainment"), UUID.randomUUID()));
        final FakeUriScraper fakeUriScraper = new FakeUriScraper(Uri.empty());
        fakeUriScraper.scrap();
        webPage.update(fakeUriScraper);
        Repositories.webPages().add(webPage);
        final ClientResource resource = restlet.newClientResource("/webpages/http://slate.fr/story/36777/avenir-home-entertainment");

        resource.get();

        assertThat(resource.getStatus(), is(Status.REDIRECTION_PERMANENT));
        assertThat(resource.getLocationRef().toString(), containsString("/webpages/http://www.slate.fr/story/36777/avenir-home-entertainment"));
    }

    @Test
    public void canRepresentNonExistingWebPage() {
        final String uri = "http://test.com";
        final ClientResource clientResource = resourceFor(uri);

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) clientResource.get();

        assertThat(clientResource.getStatus(), is(Status.CLIENT_ERROR_NOT_FOUND));
        final Map<String, Object> dataModel = representation.getDataModel();
        assertThat(dataModel, hasEntry("uri", (Object) uri));
    }

    @Test
    public void fragmentIsStillHere() {
        final String uri = "http%3A%2F%2Ftest.com%23thisisafragment";
        final ClientResource clientResource = resourceFor(uri);

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) clientResource.get();

        assertThat(clientResource.getStatus(), is(Status.CLIENT_ERROR_NOT_FOUND));
        final Map<String, Object> dataModel = representation.getDataModel();
        assertThat(dataModel, hasEntry("uri", (Object) URIs.decode(uri)));
    }

    @Test
    public void canRepresentExistingWebPage() {
        final String uri = "http://test.com";
        final WebPage webPage1 = new WebPage(new Association(new Uri(uri), UUID.randomUUID()));
        final FakeUriScraper fakeUriScraper = new FakeUriScraper(Uri.empty());
        fakeUriScraper.scrap();
        webPage1.update(fakeUriScraper);
        Repositories.webPages().add(webPage1);
        final WebPage webPage = webPage1;
        final ClientResource resource = resourceFor(uri);

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        final Map<String, Object> dataModel = representation.getDataModel();
        assertThat(dataModel, hasEntry("webPage", (Object) webPage));
    }

    @Test
    public void canCatch400Error() throws IOException {
        final ClientResource resource = resourceFor("http://404url");

        resource.get();

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void canDealWithQueryParamters() {
        final String uri = "http://test.com?param=tonpere";
        final ClientResource clientResource = resourceFor(uri);

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) clientResource.get();

        assertThat(clientResource.getStatus(), is(Status.CLIENT_ERROR_NOT_FOUND));
        final Map<String, Object> dataModel = representation.getDataModel();
        assertThat(dataModel, hasEntry("uri", (Object) uri));
    }

    private ClientResource resourceFor(final String uri) {
        return restlet.newClientResource("/webpages/" + uri);
    }
}
