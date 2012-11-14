package com.feelhub.web.resources.api;

import com.feelhub.domain.keyword.uri.Uri;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import org.junit.*;
import org.restlet.data.Status;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsApiUriResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canGetTopicIdOfKeyword() {
        final Uri uri = TestFactories.keywords().newUri();
        final ClientResource clientResource = restlet.newClientResource("/api/uri?keywordValue=" + uri.getValue());

        clientResource.get();

        assertThat(clientResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void errorIfNoTopic() {
        final ClientResource clientResource = restlet.newClientResource("/api/uri?keywordValue=http://www.google.fr");

        clientResource.get();

        assertThat(clientResource.getStatus(), is(Status.CLIENT_ERROR_NOT_FOUND));
    }

    @Test
    public void canQueryForEncodedUri() throws UnsupportedEncodingException {
        final String value = "http://www.myuri.com/test?q=test#23";
        final Uri uri = TestFactories.keywords().newUri(value);
        final ClientResource clientResource = restlet.newClientResource("/api/uri?keywordValue=" + URLEncoder.encode(uri.getValue(), "UTF-8"));

        clientResource.get();

        assertThat(clientResource.getStatus(), is(Status.SUCCESS_OK));
    }
}
