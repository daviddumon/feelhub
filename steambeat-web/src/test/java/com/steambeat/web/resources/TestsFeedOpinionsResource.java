package com.steambeat.web.resources;

import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.feed.Feed;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.SystemTime;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.domain.opinion.*;
import com.steambeat.web.*;
import org.hamcrest.Matchers;
import org.junit.*;
import org.restlet.Context;
import org.restlet.data.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsFeedOpinionsResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canPostOpinion() {
        final Subject feed = TestFactories.feeds().newFeed("http://test.com");
        final Form form = getGoodForm();
        final ClientResource resource = restlet.newClientResource("/feeds/http://test.com/opinions");

        resource.post(form);

        assertThat(resource.getStatus(), is(Status.SUCCESS_CREATED));
        assertThat(Repositories.opinions().getAll().size(), is(1));
        final Opinion opinion = Repositories.opinions().getAll().get(0);
        assertThat(opinion.getFeeling(), Matchers.is(Feeling.good));
        assertThat(opinion.getSubject(), is(feed));
    }

    @Test
    public void canPostOpinionToEncodedResource() {
        final Feed feed = TestFactories.feeds().newFeed("http://test.com%2ftata");
        final Form form = getGoodForm();
        final ClientResource resource = restlet.newClientResource("/feeds/test.com%2Ftata/opinions");

        resource.post(form);

        assertThat(resource.getStatus(), is(Status.SUCCESS_CREATED));
        final List<Opinion> opinions = Repositories.opinions().getAll();
        assertThat(opinions.size(), is(1));
        assertThat(opinions.get(0).getSubject(), is((Subject) feed));
    }

    @Test
    public void throwExceptionWhenUnknownFeed() {
        final ClientResource resource = restlet.newClientResource("/feeds/http://test.net/opinions");
        final Form form = new Form();
        form.add("value", "my opinion");
        form.add("feeling", "bad");

        resource.post(form);

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void postOpinionsToCanonicalUri() {
        final Feed feed = TestFactories.feeds().newFeed("http://www.lemonde.fr");
        final String firstUri = "lemonde.fr";
        final String secondUri = "http://lemonde.fr";
        final ClientResource firstResource = restlet.newClientResource("/feeds/" + firstUri + "/opinions");
        final ClientResource secondResource = restlet.newClientResource("/feeds/" + secondUri + "/opinions");
        final Form form = getGoodForm();

        firstResource.post(form);
        secondResource.post(form);

        final List<Opinion> opinions = Repositories.opinions().getAll();
        assertThat(opinions.size(), is(2));
        assertThat(opinions.get(0).getSubject(), is((Subject) feed));
    }

    @Test
    public void canNotPostAOpinionWithoutFeeling() {
        final Form form = new Form();
        form.add("value", "my opinion");
        final ClientResource resource = restlet.newClientResource("/feeds/www.lemonde.fr/opinions");

        resource.post(form);

        assertThat(resource.getStatus().isError(), is(true));
        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void postOpinionRedirectOnFirstPage() {
        TestFactories.feeds().newFeed("http://test.com");
        final Form form = getGoodForm();
        final ClientResource resource = restlet.newClientResource("/feeds/http://test.com/opinions");

        resource.post(form);

        assertThat(resource.getLocationRef().toString(), is(new ReferenceBuilder(Context.getCurrent()).buildUri("/feeds/http://test.com")));
    }

    private Form getGoodForm() {
        final Form form = new Form();
        form.add("value", "my opinion");
        form.add("feeling", "good");
        return form;
    }
}
