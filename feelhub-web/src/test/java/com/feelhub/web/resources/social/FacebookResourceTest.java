package com.feelhub.web.resources.social;

import com.feelhub.application.UserService;
import com.feelhub.domain.user.UserFactory;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.authentification.AuthRequest;
import com.feelhub.web.authentification.AuthenticationManager;
import com.feelhub.web.resources.authentification.AuthMethod;
import com.feelhub.web.social.FacebookConnector;
import com.restfb.types.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.scribe.model.Token;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class FacebookResourceTest {

	@Rule
	public WithFakeRepositories repositories = new WithFakeRepositories();

	@Before
	public void setUp() throws Exception {
		final Context context = ContextTestFactory.buildContext();
		authenticationManager = mock(AuthenticationManager.class);
		facebookConnector = mock(FacebookConnector.class);
		facebookResource = new FacebookResource(facebookConnector, new UserService(new UserFactory()), authenticationManager);
		final Request request = new Request(Method.GET, "http://test.com?code=toto");
		facebookResource.init(context, request, new Response(request));
	}

	@Test
	public void canCreateUserFromFacebook() {
		validUser();

		facebookResource.facebookReturn();

		final com.feelhub.domain.user.User user = Repositories.users().get("toto@gmail.com");
		assertThat(user).isNotNull();
		assertThat(user.getEmail()).isEqualTo("toto@gmail.com");
		assertThat(user.getLanguageCode()).isEqualTo("fr_fr");
	}

	@Test
	public void canAuthenticateUser() {
		validUser();

		facebookResource.facebookReturn();

		final ArgumentCaptor<AuthRequest> captor = ArgumentCaptor.forClass(AuthRequest.class);
		verify(authenticationManager).authenticate(captor.capture());
		final AuthRequest authRequest = captor.getValue();
		assertThat(authRequest).isNotNull();
		assertThat(authRequest.getEmail()).isEqualTo("toto@gmail.com");
		assertThat(authRequest.getAuthMethod()).isEqualTo(AuthMethod.FACEBOOK);
	}

	@Test
	public void redirectOnFrontPage() {
		validUser();

		facebookResource.facebookReturn();

		assertThat(facebookResource.getLocationRef().toString()).isEqualTo("https://thedomain/");
		assertThat(facebookResource.getStatus()).isEqualTo(Status.REDIRECTION_TEMPORARY);
	}

	private void validUser() {
		final FakeFbUser fbUser = new FakeFbUser();
		fbUser.email = "toto@gmail.com";
		fbUser.locale = "fr_FR";
		when(facebookConnector.getUser(any(Token.class))).thenReturn(fbUser);
	}

	private FacebookConnector facebookConnector;


	private FacebookResource facebookResource;

	private class FakeFbUser extends User{
		public String email;
		public String locale;

		@Override
		public String getEmail() {
			return email;
		}

		@Override
		public String getLocale() {
			return locale;
		}
	}

	private AuthenticationManager authenticationManager;
}
