package com.feelhub.web.resources.authentification;

import com.feelhub.application.command.*;
import com.feelhub.application.command.user.activation.ConfirmActivationCommand;
import com.feelhub.domain.feeling.SentimentValue;
import com.feelhub.domain.user.activation.ActivationNotFoundException;
import com.feelhub.repositories.SessionProvider;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.dto.FeelhubMessage;
import com.feelhub.web.guice.DummySessionProvider;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.ApiFeelingSearch;
import com.feelhub.web.search.FeelingSearch;
import com.feelhub.web.search.fake.FakeFeelingSearch;
import com.google.common.util.concurrent.Futures;
import com.google.inject.*;
import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.restlet.data.Status;

import java.util.*;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class ActivationResourceTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(FeelingSearch.class).to(FakeFeelingSearch.class);
                bind(SessionProvider.class).to(DummySessionProvider.class);
            }
        });
        apiFeelingSearch = injector.getInstance(ApiFeelingSearch.class);
    }

    @Test
    public void canConfirm() {
        final CommandBus bus = mock(CommandBus.class);
        when(bus.execute(any(Command.class))).thenReturn(Futures.immediateFuture(null));
        final ActivationResource resource = new ActivationResource(bus, apiFeelingSearch);
        ContextTestFactory.initResource(resource);
        final UUID uuid = UUID.randomUUID();
        resource.getRequest().getAttributes().put("secret", uuid.toString());

        final ModelAndView view = resource.confirm();

        final ArgumentCaptor<ConfirmActivationCommand> captor = ArgumentCaptor.forClass(ConfirmActivationCommand.class);
        verify(bus).execute(captor.capture());
        assertThat(captor.getValue().activationId).isEqualTo(uuid);
        assertThat(view.getTemplate()).isEqualTo("home.ftl");
    }

    @Test
    public void redirectToErrorIfNoActivation() {
        final CommandBus bus = mock(CommandBus.class);
        when(bus.execute(any(Command.class))).thenReturn(Futures.immediateFailedFuture(new ActivationNotFoundException()));
        final ActivationResource resource = new ActivationResource(bus, apiFeelingSearch);
        ContextTestFactory.initResource(resource);
        resource.getRequest().getAttributes().put("secret", UUID.randomUUID());

        resource.confirm();

        assertThat(resource.getStatus()).isEqualTo(Status.REDIRECTION_PERMANENT);
    }

    @Test
    public void hasActivationMessageInData() {
        final CommandBus bus = mock(CommandBus.class);
        when(bus.execute(any(Command.class))).thenReturn(Futures.immediateFuture(null));
        final ActivationResource resource = new ActivationResource(bus, apiFeelingSearch);
        ContextTestFactory.initResource(resource);
        final UUID uuid = UUID.randomUUID();
        resource.getRequest().getAttributes().put("secret", uuid.toString());

        final ModelAndView view = resource.confirm();

        final List<FeelhubMessage> messages = view.getData("messages");
        assertThat(messages).isNotNull();
        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0).getFeeling()).isEqualTo(SentimentValue.good.toString());
        assertThat(messages.get(0).getSecondTimer()).isEqualTo(3);
    }

    private ApiFeelingSearch apiFeelingSearch;
}
