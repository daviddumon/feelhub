package com.steambeat.web.resources;

import com.steambeat.web.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

public class TestsWelcomeResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void isMapped() {
        final ClientResource welcome = restlet.newClientResource("/welcome");

        welcome.get();

        assertThat(welcome.getStatus(), is(Status.SUCCESS_OK));
    }
}
