package com.steambeat.web.resources;

import com.steambeat.web.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsAssociationResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void isMapped() {
        final ClientResource associationResource = restlet.newClientResource("/association");

        associationResource.get();

        assertThat(associationResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetWithQueryString() {
        final ClientResource associationResource = restlet.newClientResource("/association?q=test");

        associationResource.get();

        assertThat(associationResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void canGetAssociationForAWord() {
        final ClientResource association = restlet.newClientResource("/association?words=test");

        association.get();


    }
}
