package com.feelhub.web.resources.authentification;

import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
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
        final User user = TestFactories.users().createFakeUser("mail@mail.com");
        final ClientResource activation = restlet.newClientResource("/activation/" + user.getSecret());

        activation.get();

        assertThat(activation.getStatus(), is(Status.SUCCESS_OK));
        assertThat(Repositories.users().getAll().get(0).isActive(), is(true));
    }
}
