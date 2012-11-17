package com.feelhub.web.resources.api;

import com.feelhub.application.UriService;
import com.feelhub.domain.keyword.uri.Uri;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.representation.ModelAndView;
import org.junit.*;
import org.restlet.data.Status;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class TestsApiUriResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canGetUriId() {
        final Uri uri = TestFactories.keywords().newUri();
        final ClientResource clientResource = restlet.newClientResource("/api/uri/" + uri.getValue());

        clientResource.get();

        assertThat(clientResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void errorIfUriDoesNotExist() {
        final ClientResource clientResource = restlet.newClientResource("/api/uri/http://www.google.fr");

        clientResource.get();

        assertThat(clientResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_NOT_FOUND);
    }

    @Test
    public void canQueryForEncodedUri() throws UnsupportedEncodingException {
        final String value = "http://www.myuri.com/test?q=test#23";
        final Uri uri = TestFactories.keywords().newUri(value);
        final ClientResource clientResource = restlet.newClientResource("/api/uri/" + URLEncoder.encode(uri.getValue(), "UTF-8"));

        clientResource.get();

        assertThat(clientResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void dataModelContainsUriId() {
        final Uri uri = TestFactories.keywords().newUri();
        final UriService uriService = mock(UriService.class);
        final ApiUriResource apiUriResource = new ApiUriResource(uriService);
        when(uriService.lookUp(uri.getValue())).thenReturn(uri);
        ContextTestFactory.initResource(apiUriResource);
        apiUriResource.getRequestAttributes().put("value", uri.getValue());

        final ModelAndView id = apiUriResource.getId();

        assertThat(id.getData("id")).isEqualTo(uri.getId());
    }
}
