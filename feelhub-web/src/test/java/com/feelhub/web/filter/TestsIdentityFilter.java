package com.feelhub.web.filter;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.WebApplicationTester;
import com.feelhub.web.authentification.AuthenticationManager;
import com.google.inject.*;
import org.junit.*;
import org.restlet.*;

import static org.mockito.Mockito.*;

public class TestsIdentityFilter {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void before() {
        authenticationManager = mock(AuthenticationManager.class);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(AuthenticationManager.class).toInstance(authenticationManager);
            }
        });
        identityFilter = injector.getInstance(IdentityFilter.class);
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
