package com.feelhub.web.resources.authentification;

import com.feelhub.domain.session.Session;
import com.feelhub.domain.user.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.test.*;
import com.feelhub.web.*;
import com.feelhub.web.authentification.*;
import com.feelhub.web.guice.GuiceTestModule;
import com.feelhub.web.tools.CookieManager;
import com.google.inject.*;
import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.restlet.data.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SessionsResourceTest {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    private AuthenticationManager authenticationManager;
    private SessionsResource resource;

    @Before
    public void avant() {
        final Injector injector = Guice.createInjector(new GuiceTestModule());
        authenticationManager = mock(AuthenticationManager.class);
        resource = new SessionsResource(authenticationManager);
        ContextTestFactory.initResource(resource);
    }

    @Test
    public void isMapped() {
        final User user = TestFactories.users().createActiveUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Form parameters = new Form();
        parameters.add("email", user.getEmail());
        parameters.add("password", "password");

        sessions.post(parameters);

        assertThat(sessions.getStatus(), is(Status.SUCCESS_CREATED));
    }

    @Test
    public void canAuthenticate() {
        final Form parameters = new Form();
        parameters.add("email", "test@test.com");
        parameters.add("password", "password");

        resource.login(parameters);

        final ArgumentCaptor<AuthRequest> captor = ArgumentCaptor.forClass(AuthRequest.class);
        verify(authenticationManager).authenticate(captor.capture());
        final AuthRequest auth = captor.getValue();
        assertThat(auth.getAuthMethod(), is(AuthMethod.FEELHUB));
        assertThat(auth.getUserId(), is("test@test.com"));
        assertThat(auth.getPassword(), is("password"));
        assertThat(auth.isRemember(), is(false));
        assertThat(resource.getStatus(), is(Status.SUCCESS_CREATED));
    }

    @Test
    public void lowerCaseMail() {
        final Form parameters = new Form();
        parameters.add("email", "TEst@test.com");
        parameters.add("password", "password");

        resource.login(parameters);

        final ArgumentCaptor<AuthRequest> captor = ArgumentCaptor.forClass(AuthRequest.class);
        verify(authenticationManager).authenticate(captor.capture());
        final AuthRequest auth = captor.getValue();
        assertThat(auth.getUserId(), is("test@test.com"));
    }

    @Test
    public void sayUnauthorizedOnAuthFail() {
        final Form parameters = new Form();
        parameters.add("email", "toto");
        parameters.add("password", "titi");
        doThrow(BadPasswordException.class).when(authenticationManager).authenticate(org.mockito.Matchers.any(AuthRequest.class));

        resource.login(parameters);

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_UNAUTHORIZED));
    }

    @Test
    public void canValidateForm() {
        resource.login(new Form());

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void canLogout() {
        when(authenticationManager.logout()).thenReturn(true);

        resource.logout();

        verify(authenticationManager).logout();
        assertThat(resource.getStatus(), is(Status.SUCCESS_ACCEPTED));
    }

    @Test
    public void setBadRequestOnLogoutFail() {
        when(authenticationManager.logout()).thenReturn(false);

        resource.logout();

        assertThat(resource.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
    }

    @Test
    public void cannotDeleteWithoutGoodCookies() {
        final User user = TestFactories.users().createActiveUser("mail@mail.com");
        final ClientResource sessions = restlet.newClientResource("/sessions");
        final Session session = TestFactories.sessions().createSessionFor(user);

        sessions.delete(null);

        assertThat(sessions.getStatus(), is(Status.CLIENT_ERROR_BAD_REQUEST));
        assertThat(Repositories.sessions().getAll().size(), is(1));
    }

    private CookieManager cookieManager;
}
