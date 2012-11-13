package com.feelhub.web.resources.social;

import com.feelhub.application.UserService;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.authentification.AuthMethod;
import com.feelhub.web.authentification.AuthRequest;
import com.feelhub.web.authentification.AuthenticationManager;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.social.FacebookConnector;
import com.restfb.types.User;
import org.joda.time.DateTime;
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

public class TestsFacebookResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        final Context context = ContextTestFactory.buildContext();
        authenticationManager = mock(AuthenticationManager.class);
        facebookConnector = mock(FacebookConnector.class);
        when(facebookConnector.getAccesToken(anyString())).thenReturn(new Token("token", "secret"));
        userService = mock(UserService.class);
        facebookResource = new FacebookResource(facebookConnector, userService, authenticationManager);
        final Request request = new Request(Method.GET, "http://test.com?code=toto");
        facebookResource.init(context, request, new Response(request));
    }

    @Test
    public void canCreateUserFromFacebook() {
        newUser();
        validUser();

        facebookResource.facebookReturn();

        verify(userService).findOrCreateForFacebook("test", "toto@gmail.com", "Jb", "Dusse", "fr_FR", "token");
    }

    @Test
    public void canAuthenticateUser() {
        newUser();
        validUser();

        facebookResource.facebookReturn();

        final ArgumentCaptor<AuthRequest> captor = ArgumentCaptor.forClass(AuthRequest.class);
        verify(authenticationManager).authenticate(captor.capture());
        final AuthRequest authRequest = captor.getValue();
        assertThat(authRequest).isNotNull();
        assertThat(authRequest.getUserId()).isEqualTo("test");
        assertThat(authRequest.getAuthMethod()).isEqualTo(AuthMethod.FACEBOOK);
    }

    @Test
    public void redirectOnFrontPage() {
        oldUser();
        validUser();

        facebookResource.facebookReturn();

        assertThat(facebookResource.getLocationRef().toString()).isEqualTo("https://thedomain/");
        assertThat(facebookResource.getStatus()).isEqualTo(Status.REDIRECTION_TEMPORARY);
    }

    @Test
    public void welcomeNewUser() {
        newUser();
        validUser();

        final ModelAndView modelAndView = facebookResource.facebookReturn();

        assertThat(facebookResource.getLocationRef()).isNull();
        assertThat(facebookResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
        assertThat(modelAndView.getTemplate()).isEqualTo("social/welcome.ftl");
    }

    private void validUser() {
        final FakeFbUser fbUser = new FakeFbUser();
        fbUser.email = "toto@gmail.com";
        fbUser.locale = "fr_FR";
        fbUser.id = "test";
        when(facebookConnector.getUser(any(Token.class))).thenReturn(fbUser);
    }

    private void newUser() {
        when(userService.findOrCreateForFacebook(anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new com.feelhub.domain.user.User("test"));
    }

    private void oldUser() {
        final com.feelhub.domain.user.User user = new com.feelhub.domain.user.User("test");
        when(userService.findOrCreateForFacebook(anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(user);
        user.setCreationDate(DateTime.now().minusDays(1));
    }

    private FacebookConnector facebookConnector;


    private FacebookResource facebookResource;

    private class FakeFbUser extends User {
        public String email;
        public String locale;
        public String id;

        @Override
        public String getEmail() {
            return email;
        }

        @Override
        public String getLocale() {
            return locale;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getFirstName() {
            return "Jb";
        }

        @Override
        public String getLastName() {
            return "Dusse";
        }
    }

    private AuthenticationManager authenticationManager;
}
