package com.steambeat.web.resources;

import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.web.*;
import org.json.JSONException;
import org.junit.*;
import org.restlet.Context;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsBookmarkletResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canRedirectToWebPage() throws IOException, JSONException {
        final WebPage webPage = TestFactories.subjects().newWebPage();
        final ClientResource bookmarkletResource = restlet.newClientResource("/bookmarklet?version=1&q=" + webPage.getRealUri().toString());

        bookmarkletResource.get();

        assertThat(bookmarkletResource.getStatus(), is(Status.REDIRECTION_SEE_OTHER));
        final String uriToRedirect = new ReferenceBuilder(Context.getCurrent()).buildUri("/webpages/" + webPage.getSemanticDescription() + "/" + webPage.getId());
        assertThat(bookmarkletResource.getLocationRef().toString(), is(uriToRedirect));
    }

    @Test
    public void throwAnErrorIfNoParameter() {
        final ClientResource subjectsResource = restlet.newClientResource("/bookmarklet?version=1");

        subjectsResource.get();

        assertThat(subjectsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void canRedirectToNewWebPageFtl() {
        final ClientResource bookmarkletResource = restlet.newClientResource("/bookmarklet?q=http://www.lemonde.Fr&version=1");

        bookmarkletResource.get();

        assertThat(bookmarkletResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void throwAnErrorIfEmptyParameter() {
        final ClientResource subjectsResource = restlet.newClientResource("/bookmarklet?q=&version=1");

        subjectsResource.get();

        assertThat(subjectsResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void canCheckVersion() {
        final ClientResource bookmarkletResource = restlet.newClientResource("/bookmarklet?q=http://www.lemonde.Fr&version=1");

        bookmarkletResource.get();

        assertThat(bookmarkletResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void throwErrorIfNoVersion() {
        final ClientResource bookmarkletResource = restlet.newClientResource("/bookmarklet?q=http://www.lemonde.Fr");

        bookmarkletResource.get();

        assertThat(bookmarkletResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void proposeToChangeIfBadVersion() throws IOException {
        final ClientResource bookmarkletResource = restlet.newClientResource("/bookmarklet?q=http://www.lemonde.Fr&version=2");

        final Representation representation = bookmarkletResource.get();

        assertThat(bookmarkletResource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
        assertThat(representation.getText(), containsString("outdated"));
    }
}
