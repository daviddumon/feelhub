package com.feelhub.web.resources.authentification;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.repositories.Repositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import org.junit.*;
import org.restlet.data.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class SignupResourceTest {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Test
    public void canSignup() {
        final ClientResource signup = restlet.newClientResource("/signup");
        final Form parameters = new Form();
        final String email = "mail@mail.com";
        parameters.add("fullname", "fullname");
        parameters.add("language", "en");
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
        TestFactories.users().createFakeUser(email);
        final Form parameters = new Form();
        parameters.add("email", email);
        parameters.add("password", "password");
        parameters.add("fullname", "");
        parameters.add("language", "");

        signup.post(parameters);

        assertThat(signup.getStatus(), is(Status.CLIENT_ERROR_CONFLICT));
    }

    @Test
    public void returnErrorOnBadEmail() {
        final ClientResource signup = restlet.newClientResource("/signup");
        final Form parameters = new Form();
        parameters.add("email", "mail.com");
        parameters.add("password", "password");
        parameters.add("fullname", "");
        parameters.add("language", "");

        signup.post(parameters);

        assertThat(signup.getStatus(), is(Status.CLIENT_ERROR_PRECONDITION_FAILED));
    }

    @Test
    public void returnBadRequestIfMissingParameter() {
        final ClientResource signup = restlet.newClientResource("/signup");
        final Form parameters = new Form();
        final String email = "mail@mail.com";
        parameters.add("fullname", "");
        parameters.add("email", email);
        parameters.add("password", "password");

        signup.post(parameters);

        assertThat(signup.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }
}
