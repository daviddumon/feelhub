package com.steambeat.web.resources;

import com.steambeat.domain.opinion.Feeling;
import com.steambeat.domain.subject.feed.Feed;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.tools.URIs;
import com.steambeat.web.*;
import org.junit.*;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsFeedResource {

    @Rule
    public final WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Test
    public void isMapped() throws IOException {
        TestFactories.feeds().newFeed("http://www.google.fr");
        final ClientResource resource = restlet.newClientResource("/feeds/http%3A%2F%2Fwww.google.fr");

        final Representation response = resource.get();

        assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canDealWithHierarchicalUri() throws IOException {
        TestFactories.feeds().newFeed("http://www.slate.fr/story/36777/avenir-home-entertainment");
        final ClientResource resource = restlet.newClientResource("/feeds/http://www.slate.fr/story/36777/avenir-home-entertainment");

        final Representation representation = resource.get();

        assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canDealWithRedirection() {
        TestFactories.feeds().newFeed("http://www.slate.fr/story/36777/avenir-home-entertainment");
        final ClientResource resource = restlet.newClientResource("/feeds/http://slate.fr/story/36777/avenir-home-entertainment");

        resource.get();

        assertThat(resource.getStatus(), is(Status.REDIRECTION_PERMANENT));
        assertThat(resource.getLocationRef().toString(), containsString("/feeds/http://www.slate.fr/story/36777/avenir-home-entertainment"));
    }

    @Test
    public void canRepresentNonExistingFeed() {
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
    public void canRepresentExistingFeed() {
        final String uri = "http://test.com";
        final Feed feed = TestFactories.feeds().newFeed(uri);
        final ClientResource resource = resourceFor(uri);

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        final Map<String, Object> dataModel = representation.getDataModel();
        assertThat(dataModel, hasEntry("feed", (Object) feed));
    }

    @Test
    public void showFirst20CommentsByDefault() {
        final String uri = "http://test.com";
        getFeedWith30Opinions(uri);
        final ClientResource resource = resourceFor(uri);

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        final Map<String, Object> dataModel = representation.getDataModel();
        assertThat(dataModel, hasKey("page"));
        final Page page = representation.getData("page");
        assertThat(page.getOpinions().size(), is(20));
        assertThat(page.getOpinions().get(0).getText(), is("comment #29"));
        assertThat(page.getNumber(), is(1));
    }

    @Test
    public void hasPages() {
        final String uri = "http://test.com";
        getFeedWith30Opinions(uri);
        final ClientResource resource = resourceFor(uri);

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        final Map<String, Object> dataModel = representation.getDataModel();
        assertThat(dataModel, hasKey("pages"));
        final List<Integer> pages = representation.getData("pages");
        assertThat(pages.size(), is(2));
        assertThat(pages.get(0), is(1));
        assertThat(pages.get(1), is(2));
    }

    @Test
    public void canPaginate() {
        final String uri = "http://test.com";
        getFeedWith30Opinions(uri);
        final ClientResource resource = resourceFor(uri + "/2");
        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        final Page page = representation.getData("page");
        assertThat(page.getOpinions().size(), is(10));
        assertThat(page.getNumber(), is(2));
    }

    @Test
    public void canCorrectPageNumber() {
        final String uri = "http://test.com";
        getFeedWith30Opinions(uri);
        final ClientResource resource = resourceFor(uri + "/3");

        final SteambeatTemplateRepresentation representation = (SteambeatTemplateRepresentation) resource.get();

        assertThat(representation.<Page>getData("page").getNumber(), is(1));
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

    private Feed getFeedWith30Opinions(final String resource) {
        final Feed feed = TestFactories.feeds().newFeed(resource);
        Repositories.feeds().add(feed);
        for (int i = 0; i < 30; i++) {
            Repositories.opinions().add(feed.createOpinion("comment #" + i, Feeling.good));
        }
        return feed;
    }

    private ClientResource resourceFor(final String uri) {
        return restlet.newClientResource("/feeds/" + uri);
    }
}
