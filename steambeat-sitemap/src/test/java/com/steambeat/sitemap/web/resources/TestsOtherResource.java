package com.steambeat.sitemap.web.resources;

import com.steambeat.sitemap.web.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
