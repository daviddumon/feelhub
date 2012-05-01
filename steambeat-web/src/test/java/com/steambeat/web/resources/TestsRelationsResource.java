package com.steambeat.web.resources;

import com.steambeat.web.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestsRelationsResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void isMapped() {
        final ClientResource relationsResource = restlet.newClientResource("/relations");

        relationsResource.get();

        assertThat(relationsResource.getStatus(), is(Status.SUCCESS_OK));
    }

}
