package com.feelhub.web.resources;

import com.feelhub.web.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsMyFeelingsResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void redirectIfNotIdentified() {
        final ClientResource resource = restlet.newClientResource("/myfeelings");

        resource.get();

        assertThat(resource.getStatus(), is(Status.REDIRECTION_SEE_OTHER));
    }
}
