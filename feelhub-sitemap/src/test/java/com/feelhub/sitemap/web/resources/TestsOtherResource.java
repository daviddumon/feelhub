package com.feelhub.sitemap.web.resources;

import com.feelhub.sitemap.web.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOtherResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void isMapped() {
        final ClientResource clientResource = restlet.newClientResource("/");

        clientResource.get();

        assertThat(clientResource.getStatus(), is(Status.REDIRECTION_PERMANENT));
    }
}
