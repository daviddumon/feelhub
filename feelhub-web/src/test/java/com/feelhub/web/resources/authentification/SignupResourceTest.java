package com.feelhub.web.resources.authentification;

import com.feelhub.application.command.*;
import com.feelhub.application.command.user.CreateUserCommand;
import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.session.EmailAlreadyUsed;
import com.feelhub.domain.user.BadEmail;
import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.social.FacebookConnector;
import com.google.common.util.concurrent.Futures;
import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.restlet.data.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;
import static org.fest.assertions.MapAssert.*;
import static org.mockito.Mockito.*;

public class SignupResourceTest {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void setUp() throws Exception {
        commandBus = mock(CommandBus.class);
        final FacebookConnector facebookConnector = mock(FacebookConnector.class);
        when(facebookConnector.getUrl()).thenReturn("toto");
        resource = new SignupResource(facebookConnector, commandBus);
        ContextTestFactory.initResource(resource);
    }

    @Test
    public void canSignup() {
        when(commandBus.execute(any(CreateUserCommand.class))).thenReturn(Futures.immediateFuture(UUID.randomUUID()));
        final Form parameters = new Form();
        final String email = "mail@mail.com";
        parameters.add("fullname", "fullname");
        parameters.add("language", "en");
        parameters.add("email", email);
        parameters.add("password", "password");

        resource.signup(parameters);

        assertThat(resource.getStatus()).isEqualTo(Status.SUCCESS_CREATED);
        final ArgumentCaptor<CreateUserCommand> captor = ArgumentCaptor.forClass(CreateUserCommand.class);
        verify(commandBus).execute(captor.capture());
        final CreateUserCommand command = captor.getValue();
        assertThat(command.email).isEqualTo(email);
        assertThat(command.fullname).isEqualTo("fullname");
    }

    @Test
    public void returnErrorOnKnownEmail() {
        when(commandBus.execute(any(Command.class))).thenReturn(Futures.immediateFailedFuture(new EmailAlreadyUsed()));
        final String email = "mail@mail.com";
        final Form parameters = new Form();
        parameters.add("email", email);
        parameters.add("password", "password");
        parameters.add("fullname", "");
        parameters.add("language", "");

        resource.signup(parameters);

        assertThat(resource.getStatus()).isEqualTo(Status.CLIENT_ERROR_CONFLICT);
    }

    @Test
    public void returnErrorOnBadEmail() {
        when(commandBus.execute(any(Command.class))).thenReturn(Futures.immediateFailedFuture(new BadEmail()));
        final Form parameters = new Form();
        parameters.add("email", "mail.com");
        parameters.add("password", "password");
        parameters.add("fullname", "");
        parameters.add("language", "");

        resource.signup(parameters);

        assertThat(resource.getStatus()).isEqualTo(Status.CLIENT_ERROR_PRECONDITION_FAILED);
    }

    @Test
    public void returnBadRequestIfMissingParameter() {
        final Form parameters = new Form();
        final String email = "mail@mail.com";
        parameters.add("fullname", "");
        parameters.add("email", email);
        parameters.add("password", "password");

        resource.signup(parameters);

        assertThat(resource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void canPassFavoriteLanguage() {
        resource.getRequest().getClientInfo().getAcceptedLanguages().add(new Preference<Language>(new Language("fr")));

        final ModelAndView modelAndView = resource.represent();

        assertThat(modelAndView.getData()).includes(entry("preferedLanguage", "fr"));

    }

    private CommandBus commandBus;
    private SignupResource resource;
}
