package com.steambeat.web.resources;

import com.steambeat.repositories.Repositories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.web.*;
import org.junit.*;
import org.restlet.data.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSignupResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canSignup() {
        final ClientResource signup = restlet.newClientResource("/signup");
        final Form parameters = new Form();
        final String email = "mail@mail.com";
        parameters.add("email", email);
        parameters.add("password", "password");

        signup.post(parameters);

        assertThat(signup.getStatus(), is(Status.SUCCESS_CREATED));
        assertThat(Repositories.users().getAll().size(), is(1));
        assertThat(Repositories.users().getAll().get(0).getEmail(), is(email));
    }

    @Test
    public void returnErrorOnKnownEmail() {
        final ClientResource signup = restlet.newClientResource("/signup");
        final String email = "mail@mail.com";
        TestFactories.users().createUser(email);
        final Form parameters = new Form();
        parameters.add("email", email);
        parameters.add("password", "password");

        signup.post(parameters);

        assertThat(signup.getStatus(), is(Status.CLIENT_ERROR_CONFLICT));
    }
}
