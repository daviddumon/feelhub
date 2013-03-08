package com.feelhub.web.resources.social;

import com.feelhub.application.command.CommandBus;
import com.feelhub.application.command.user.CreateUserFromFacebookCommand;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.authentification.*;
import com.feelhub.web.social.FacebookConnector;
import com.google.common.util.concurrent.Futures;
import com.restfb.types.User;
import org.joda.time.DateTime;
import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.restlet.*;
import org.restlet.data.*;
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
        when(facebookConnector.getAccesToken(anyString())).thenReturn(new Token("token", "secret"));
        commandBus = mock(CommandBus.class);
        facebookResource = new FacebookResource(facebookConnector, authenticationManager, commandBus);
        final Request request = new Request(Method.GET, "http://test.com?code=toto");
        facebookResource.init(context, request, new Response(request));
    }

    @Test
    public void canCreateUserFromFacebook() {
        newUser();
        validUser();

        facebookResource.facebookReturn();

        ArgumentCaptor<CreateUserFromFacebookCommand> captor = ArgumentCaptor.forClass(CreateUserFromFacebookCommand.class);
        verify(commandBus).execute(captor.capture());
        CreateUserFromFacebookCommand command = captor.getValue();
        assertThat(command.email).isEqualTo("toto@gmail.com");
        assertThat(command.firstName).isEqualTo("Jb");
        assertThat(command.lastName).isEqualTo("Dusse");
        assertThat(command.id).isEqualTo("test");
        assertThat(command.token).isEqualTo("token");
        assertThat(command.language).isEqualTo("fr_FR");
    }

    @Test
    public void canAuthenticateUser() {
        final com.feelhub.domain.user.User user = newUser();
        validUser();

        facebookResource.facebookReturn();

        final ArgumentCaptor<AuthRequest> captor = ArgumentCaptor.forClass(AuthRequest.class);
        verify(authenticationManager).authenticate(captor.capture());
        final AuthRequest authRequest = captor.getValue();
        assertThat(authRequest).isNotNull();
        assertThat(authRequest.getUserId()).isEqualTo(user.getId().toString());
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

        facebookResource.facebookReturn();

        assertThat(facebookResource.getStatus()).isEqualTo(Status.REDIRECTION_TEMPORARY);
        assertThat(facebookResource.getLocationRef().toString()).isEqualTo("https://thedomain//social/welcome");
    }

    private void validUser() {
        final FakeFbUser fbUser = new FakeFbUser();
        fbUser.email = "toto@gmail.com";
        fbUser.locale = "fr_FR";
        fbUser.id = "test";
        when(facebookConnector.getUser(any(Token.class))).thenReturn(fbUser);
    }

    private com.feelhub.domain.user.User newUser() {
        final com.feelhub.domain.user.User user = new com.feelhub.domain.user.User();
        Repositories.users().add(user);
        when(commandBus.execute(any(CreateUserFromFacebookCommand.class)))
                .thenReturn(Futures.immediateFuture(user.getId()));
        return user;
    }

    private void oldUser() {
        final com.feelhub.domain.user.User user = new com.feelhub.domain.user.User();
        Repositories.users().add(user);
        when(commandBus.execute(any(CreateUserFromFacebookCommand.class)))
                .thenReturn(Futures.immediateFuture(user.getId()));
        user.setCreationDate(DateTime.now().minusDays(1));
    }

    private FacebookConnector facebookConnector;


    private FacebookResource facebookResource;
    private CommandBus commandBus;

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
