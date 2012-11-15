package com.feelhub.web.resources.authentification;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.social.FacebookConnector;
import org.junit.*;
import org.restlet.Request;
import org.restlet.data.Status;

import static org.fest.assertions.Assertions.*;
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

        assertThat(login.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void canForgeFacebookUrl() {
        final FacebookConnector facebook = mock(FacebookConnector.class);
        final LoginResource login = new LoginResource(facebook);
        login.setRequest(new Request());
        when(facebook.getUrl()).thenReturn("une url");

        final ModelAndView modelAndView = login.represent();

        assertThat(modelAndView.<String>getData("facebookUrl")).isEqualTo("une url");
    }

    @Test
    public void passReferrerToTheView() {
        final String referrer = "http://www.feelhub.com";
        final FacebookConnector facebook = mock(FacebookConnector.class);
        final LoginResource login = new LoginResource(facebook);
        final Request request = new Request();
        request.setReferrerRef(referrer);
        login.setRequest(request);
        when(facebook.getUrl()).thenReturn("une url");

        final ModelAndView modelAndView = login.represent();

        assertThat(modelAndView.<String>getData("referrer")).isEqualTo(referrer);
    }
}
