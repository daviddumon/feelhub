package com.steambeat.web.resources;

import com.steambeat.web.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsTermsResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void isMapped() {
        final ClientResource aboutResource = restlet.newClientResource("/terms");

        aboutResource.get();

        assertThat(aboutResource.getStatus(), is(Status.SUCCESS_OK));
    }
}
