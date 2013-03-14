package com.feelhub.web.resources.authentification;

import com.feelhub.application.command.*;
import com.feelhub.application.command.user.activation.ConfirmActivationCommand;
import com.feelhub.domain.user.activation.ActivationNotFoundException;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.util.concurrent.Futures;
import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.restlet.data.Status;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class ActivationResourceTest {


    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canConfirm() {
        final CommandBus bus = mock(CommandBus.class);
        when(bus.execute(any(Command.class))).thenReturn(Futures.immediateFuture(null));
        final ActivationResource resource = new ActivationResource(bus);
        ContextTestFactory.initResource(resource);
        final UUID uuid = UUID.randomUUID();
        resource.getRequest().getAttributes().put("secret", uuid.toString());

        final ModelAndView view = resource.confirm();


        final ArgumentCaptor<ConfirmActivationCommand> captor = ArgumentCaptor.forClass(ConfirmActivationCommand.class);
        verify(bus).execute(captor.capture());
        assertThat(captor.getValue().activationId).isEqualTo(uuid);
        assertThat(view.getTemplate()).isEqualTo("activation.ftl");
    }

    @Test
    public void redirectToHomeIfNoActivation() {
        final CommandBus bus = mock(CommandBus.class);
        when(bus.execute(any(Command.class))).thenReturn(Futures.immediateFailedFuture(new ActivationNotFoundException()));
        final ActivationResource resource = new ActivationResource(bus);
        ContextTestFactory.initResource(resource);
        resource.getRequest().getAttributes().put("secret", UUID.randomUUID());

        resource.confirm();

        assertThat(resource.getStatus()).isEqualTo(Status.REDIRECTION_PERMANENT);
    }
}
