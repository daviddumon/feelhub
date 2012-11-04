package com.feelhub.web.filter;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.WebApplicationTester;
import com.feelhub.web.authentification.AuthenticationManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;

import static org.mockito.Mockito.*;

public class TestsIdentityFilter {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void before() {
		authenticationManager = mock(AuthenticationManager.class);
		identityFilter = new IdentityFilter(authenticationManager);
    }



	@Test
    public void canInitCurrentUser() {
		final Request request = new Request();
		identityFilter.beforeHandle(request, new Response(request));

        verify(authenticationManager).initRequest();
    }




    private IdentityFilter identityFilter;
	private AuthenticationManager authenticationManager;
}
