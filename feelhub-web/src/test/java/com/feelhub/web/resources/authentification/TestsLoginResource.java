package com.feelhub.web.resources.authentification;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.*;
import com.feelhub.web.representation.ModelAndView;
import org.junit.*;
import org.restlet.data.Status;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

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

    @Test
    public void canForgeFacebookUrl() {
        final FacebookConnector facebook = mock(FacebookConnector.class);
        final LoginResource login = new LoginResource(facebook);
        when(facebook.getUrl()).thenReturn("une url");

        final ModelAndView modelAndView = login.represent();

        assertThat(modelAndView.<String>getData("facebookUrl"), is("une url"));
    }
}
