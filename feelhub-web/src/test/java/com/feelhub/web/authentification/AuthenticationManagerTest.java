package com.feelhub.web.authentification;

import com.feelhub.application.SessionService;
import com.feelhub.domain.session.Session;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.tools.*;
import org.joda.time.DateTime;
import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.restlet.data.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthenticationManagerTest {

    @Before
    public void setUp() throws Exception {
        cookieManager = mock(CookieManager.class);
        when(cookieManager.cookieBuilder()).thenReturn(new CookieBuilder(new FeelhubWebProperties()));
        sessionService = mock(SessionService.class);
        manager = new AuthenticationManager(sessionService, new FeelhubWebProperties(), cookieManager);
        user = TestFactories.users().createActiveUser("test@test.com");
    }

    @After
    public void tearDown() {
        CurrentUser.set(null);
    }

    @Test
    public void setCookieOnSuccessLogin() {
        when(sessionService.createSession(eq(user), any(DateTime.class))).thenReturn(new Session(DateTime.now(), user));
        manager.authenticate(new AuthRequest(user.getEmail(), "password", true));

        verify(cookieManager, times(2)).setCookie(any(CookieSetting.class));
    }

    @Test
    public void doNotSetCookieOnError() {
        try {
            manager.authenticate(new AuthRequest(user.getEmail(), "test", true));
        } catch (Exception e) {

        }

        verifyZeroInteractions(cookieManager);
    }

    @Test
    public void deleteSessionOnLogout() {
        final Session session = new Session(DateTime.now(), user);
        when(cookieManager.getCookie("session")).thenReturn(new Cookie(0, "session", session.getId().toString()));
        when(cookieManager.getCookie("id")).thenReturn(new Cookie(0, "id", "test"));

        manager.logout();

        verify(sessionService).deleteSession((UUID) session.getId());
    }

    @Test
    public void deleteCookiesOnLogout() {
        final Session session = new Session(DateTime.now(), user);
        when(cookieManager.getCookie("session")).thenReturn(new Cookie(0, "session", session.getId().toString()));
        when(cookieManager.getCookie("id")).thenReturn(new Cookie(0, "id", "test"));

        manager.logout();

        verify(cookieManager, times(2)).setCookie(any(CookieSetting.class));
    }

    @Test
    public void canSetCurrentUser() {
        authenticate();

        manager.initRequest();

        assertThat(CurrentUser.get()).isNotNull();
        assertThat(CurrentUser.get().isAuthenticated()).isTrue();
    }

    private void authenticate() {
        cookieWithKnownUser();
        when(sessionService.authentificate(any(User.class), any(UUID.class))).thenReturn(true);
    }

    private void cookieWithKnownUser() {
        final CookieSetting cookieSetting = new CookieSetting();
        cookieSetting.setName("id");
        cookieSetting.setValue(user.getId().toString());
        when(cookieManager.getCookie("id")).thenReturn(cookieSetting);
    }

    @Test
    public void canSetNullUser() {
        manager.initRequest();

        assertThat(CurrentUser.get()).isNotNull();
        assertThat(CurrentUser.get().isAuthenticated()).isFalse();
        assertThat(CurrentUser.get().getFullname()).isEqualTo("anonymous");
    }

    @Test
    public void setUserOnlyIfUserExists() {
        cookieWithUnknownUser();

        manager.initRequest();

        assertThat(CurrentUser.get().isAuthenticated()).isFalse();
        assertThat(CurrentUser.get().getFullname()).isEqualTo("anonymous");
    }

    private void cookieWithUnknownUser() {
        final CookieSetting cookieSetting = new CookieSetting();
        cookieSetting.setName("id");
        cookieSetting.setValue("john@doe.com");
        when(cookieManager.getCookie("id")).thenReturn(cookieSetting);
    }

    @Test
    public void notAuthentificatedIfNoSession() {
        cookieWithKnownUser();

        manager.initRequest();

        assertThat(CurrentUser.get().isAuthenticated()).isFalse();
        assertThat(CurrentUser.get().getFullname()).isEqualTo("full name");
    }

    @Test
    public void canAuthenticateFromFacebook() {
        when(sessionService.createSession(any(User.class), any(DateTime.class))).thenReturn(new Session(DateTime.now(), user));

        manager.authenticate(AuthRequest.facebook(user.getId().toString()));

        final ArgumentCaptor<CookieSetting> captor = ArgumentCaptor.forClass(CookieSetting.class);
        verify(cookieManager, times(2)).setCookie(captor.capture());
    }

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();
    private CookieManager cookieManager;
    private SessionService sessionService;
    private AuthenticationManager manager;
    private User user;
}
