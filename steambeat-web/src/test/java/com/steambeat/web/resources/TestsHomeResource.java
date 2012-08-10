package com.steambeat.web.resources;

import com.steambeat.web.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsHomeResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void homeResourceIsMapped() {
        final ClientResource resource = restlet.newClientResource("/");

        resource.get();

        assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
    }
}
