package com.steambeat.web.resources;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.FakeUriScraper;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.web.*;
import org.junit.*;
import org.restlet.data.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsWebPagesResource {

    @Rule
    public final WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Before
    public void setUp() throws Exception {
        resource = restlet.newClientResource("/webpages");
    }

    @Test
    public void canCreate() {
        final String uri = "http://www.google.fr";

        resource.post(formWith(uri));

        assertThat(resource.getStatus(), is(Status.SUCCESS_CREATED));
        assertThat(resource.getLocationRef().toString(), containsString("/webpages/http://www.google.fr"));
        assertThat(Repositories.webPages().getAll().size(), is(1));
    }

    @Test
    public void newWebPageCreatedOnlyOnce() {
        final WebPage webPage = new WebPage(new Association(new Uri("http://www.google.fr"), UUID.randomUUID()));
        webPage.update();
        Repositories.webPages().add(webPage);

        resource.post(formWith("http://www.google.fr"));

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void takeIntoAccountQueryParameters() {
        final String uri = "http://test.com?param=2";

        resource.post(formWith(uri));

        assertThat(resource.getStatus(), is(Status.SUCCESS_CREATED));
        assertThat(resource.getLocationRef().toString(), containsString(uri));
        assertThat(Repositories.webPages().getAll().size(), is(1));
        final WebPage webPage = Repositories.webPages().get(uri);
        assertThat(webPage, notNullValue());
    }

    @Test
    public void keepFragment() {
        final String uri = "http://test.com/#p/u/2/xsJ0u7MIxLM";

        resource.post(formWith(uri));

        final WebPage webPage = Repositories.webPages().get(uri);
        assertThat(webPage, notNullValue());
    }

    private Form formWith(final String uri) {
        final Form form = new Form();
        form.add("uri", uri);
        return form;
    }

    private ClientResource resource;
}
