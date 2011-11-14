package com.steambeat.web.resources;

import com.steambeat.domain.subject.feed.Feed;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.web.*;
import org.junit.*;
import org.restlet.data.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsFeedsResource {

    @Rule
    public final WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Before
    public void setUp() throws Exception {
        resource = restlet.newClientResource("/feeds");
    }

    @Test
    public void canCreate() {
        final String uri = "http://www.google.fr";

        resource.post(formWith(uri));

        assertThat(resource.getStatus(), is(Status.SUCCESS_CREATED));
        assertThat(resource.getLocationRef().toString(), containsString("/feeds/http://www.google.fr"));
        assertThat(Repositories.feeds().getAll().size(), is(1));
    }

    @Test
    public void newFeedCreatedOnlyOnce() {
        TestFactories.feeds().newFeed("http://www.google.fr");

        resource.post(formWith("http://www.google.fr"));

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void takeIntoAccountQueryParameters() {
        final String uri = "http://test.com?param=2";

        resource.post(formWith(uri));

        assertThat(resource.getStatus(), is(Status.SUCCESS_CREATED));
        assertThat(resource.getLocationRef().toString(), containsString(uri));
        assertThat(Repositories.feeds().getAll().size(), is(1));
        final Feed feed = Repositories.feeds().get(uri);
        assertThat(feed, notNullValue());
    }

    @Test
    public void keepFragment() {
        final String uri = "http://test.com#p/u/2/xsJ0u7MIxLM";

        resource.post(formWith(uri));

        final Feed feed = Repositories.feeds().get(uri);
        assertThat(feed, notNullValue());
    }

    private Form formWith(String uri) {
        final Form form = new Form();
        form.add("uri", uri);
        return form;
    }

    private ClientResource resource;
}
