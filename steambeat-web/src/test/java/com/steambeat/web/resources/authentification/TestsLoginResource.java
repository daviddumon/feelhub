package com.steambeat.web.resources.authentification;

import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.web.ClientResource;
import com.steambeat.web.WebApplicationTester;
import com.steambeat.web.representation.ModelAndView;
import org.junit.Rule;
import org.junit.Test;
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
