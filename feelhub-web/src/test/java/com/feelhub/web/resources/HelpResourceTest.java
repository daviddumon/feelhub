package com.feelhub.web.resources;

import com.feelhub.web.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class HelpResourceTest {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void helpResourceIsMapped() {
        final ClientResource help = restlet.newClientResource("/help");

        help.get();

        assertThat(help.getStatus(), is(Status.SUCCESS_OK));
    }
}
