package com.feelhub.web.resources;

import com.feelhub.web.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class WelcomeResourceTest {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void welcomeResourceIsMapped() {
        final ClientResource welcome = restlet.newClientResource("/welcome");

        welcome.get();

        assertThat(welcome.getStatus(), is(Status.SUCCESS_OK));
    }
}
