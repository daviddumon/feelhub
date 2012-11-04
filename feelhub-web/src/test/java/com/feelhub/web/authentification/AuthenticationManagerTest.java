package com.feelhub.web.authentification;

import com.feelhub.application.SessionService;
import com.feelhub.application.UserService;
import com.feelhub.domain.session.Session;
import com.feelhub.domain.user.User;
import com.feelhub.domain.user.UserFactory;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.tools.CookieBuilder;
import com.feelhub.web.tools.CookieManager;
import com.feelhub.web.tools.FeelhubWebProperties;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.restlet.data.CookieSetting;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthenticationManagerTest {

	@Rule
	public WithFakeRepositories repositories = new WithFakeRepositories();

	@Before
	public void setUp() throws Exception {
		cookieManager = mock(CookieManager.class);
		when(cookieManager.cookieBuilder()).thenReturn(new CookieBuilder(new FeelhubWebProperties()));
		sessionService = mock(SessionService.class);
		userService = new UserService(new UserFactory());
		manager = new AuthenticationManager(userService, sessionService, new FeelhubWebProperties(), cookieManager);
		user = userService.createUser("test@test.com", "pass", "jb", "fr_FR");
		user.activate();
	}

	@Test
	public void tearDown() {
		CurrentUser.set(null);
	}

	@Test
	public void canSetCurrentUser() {
		authenticate();

		manager.initRequest();

		assertThat(CurrentUser.get()).isNotNull();
		assertThat(CurrentUser.get().isAuthenticated()).isTrue();
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
		assertThat(CurrentUser.get().getFullname()).isEqualTo("jb");
	}

	@Test
	public void canAuthenticateFromFacebook() {
		when(sessionService.createSession(any(User.class), any(DateTime.class))).thenReturn(new Session(DateTime.now()));

		manager.authenticate(AuthRequest.facebook("test@test.com"));

		final ArgumentCaptor<CookieSetting> captor = ArgumentCaptor.forClass(CookieSetting.class);
		verify(cookieManager, times(2)).setCookie(captor.capture());
	}

	private void cookieWithKnownUser() {
		final CookieSetting cookieSetting = new CookieSetting();
		cookieSetting.setName("id");
		cookieSetting.setValue(user.getId());
		when(cookieManager.getCookie("id")).thenReturn(cookieSetting);
	}

	private void authenticate() {
		cookieWithKnownUser();
		when(sessionService.authentificate(any(User.class), any(UUID.class))).thenReturn(true);
	}

	private CookieManager cookieManager;
	private SessionService sessionService;
	private UserService userService;
	private AuthenticationManager manager;
	private User user;
}
