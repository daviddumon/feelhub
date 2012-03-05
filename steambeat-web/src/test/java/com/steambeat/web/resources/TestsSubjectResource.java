package com.steambeat.web.resources;

import com.steambeat.web.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSubjectResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void canSearchWebpage() {
        final ClientResource subjectSearchResource = restlet.newClientResource("/subject?q=http://www.lemonde.fr");

        subjectSearchResource.get();

        assertThat(subjectSearchResource.getStatus(), is(Status.SUCCESS_OK));
    }
}
