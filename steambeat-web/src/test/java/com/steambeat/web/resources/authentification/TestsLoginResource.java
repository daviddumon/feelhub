package com.steambeat.web.resources.authentification;

import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.web.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsLoginResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void loginResourceIsMapped() {
        final ClientResource login = restlet.newClientResource("/login");

        login.get();

        assertThat(login.getStatus(), is(Status.SUCCESS_OK));
    }
}
