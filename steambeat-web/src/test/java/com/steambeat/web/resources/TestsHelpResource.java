package com.steambeat.web.resources;

import com.steambeat.web.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class TestsHelpResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void isMapped() {
        final ClientResource help = restlet.newClientResource("/help");

        help.get();

        assertThat(help.getStatus(), is(Status.SUCCESS_OK));
    }
}
