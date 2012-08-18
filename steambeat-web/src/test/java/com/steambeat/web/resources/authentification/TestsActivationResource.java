package com.steambeat.web.resources.authentification;

import com.steambeat.domain.user.User;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.TestFactories;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.web.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsActivationResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canActivateAnUser() {
        final User user = TestFactories.users().createUser("mail@mail.com");
        final ClientResource activation = restlet.newClientResource("/activation/" + user.getSecret());

        activation.get();

        assertThat(activation.getStatus(), is(Status.SUCCESS_OK));
        assertThat(Repositories.users().getAll().get(0).isActive(), is(true));
    }
}
