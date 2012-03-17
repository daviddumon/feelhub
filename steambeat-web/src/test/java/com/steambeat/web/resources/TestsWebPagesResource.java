package com.steambeat.web.resources;

import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.web.*;
import org.junit.*;
import org.restlet.data.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsWebPagesResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public final WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void setUp() throws Exception {
        resource = restlet.newClientResource("/webpages");
    }

    @Test
    public void canCreate() {
        final String uri = "http://www.google.fr";

        resource.post(formWith(uri));

        assertThat(resource.getStatus(), is(Status.SUCCESS_CREATED));
        assertThat(Repositories.subjects().getAll().size(), is(1));
        final WebPage webPage = (WebPage) Repositories.subjects().getAll().get(0);
        assertThat(resource.getLocationRef().toString(), containsString("/webpages/" + webPage.getId()));
    }

    @Test
    public void canCreateFromEncodedUri() {
        final String uri = "http%3A%2F%2Ftest.com%3Fparam%3D2";

        resource.post(formWith(uri));

        assertThat(resource.getStatus(), is(Status.SUCCESS_CREATED));
        assertThat(Repositories.subjects().getAll().size(), is(1));
    }

    @Test
    public void canCreateFromEncodedUriWithFragment() {
        final String uri = "http%3A%2F%2Ftest.com%2F%23p%2Fu%2F2%2FxsJ0u7MIxLM";

        resource.post(formWith(uri));

        assertThat(resource.getStatus(), is(Status.SUCCESS_CREATED));
        assertThat(Repositories.subjects().getAll().size(), is(1));
    }

    private Form formWith(final String uri) {
        final Form form = new Form();
        form.add("uri", uri);
        return form;
    }

    private ClientResource resource;
}
