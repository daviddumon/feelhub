package com.feelhub.web.resources.authentification;

import com.feelhub.application.ActivationService;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.WebApplicationTester;
import com.feelhub.web.representation.ModelAndView;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TestsActivationResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canConfirm() {
        final ActivationService activationService = mock(ActivationService.class);
        final ActivationResource resource = new ActivationResource(activationService);
        ContextTestFactory.initResource(resource);
        final UUID uuid = UUID.randomUUID();
        resource.getRequest().getAttributes().put("secret", uuid.toString());

        final ModelAndView view = resource.confirm();

        verify(activationService).confirm(uuid);
        assertThat(view.getTemplate()).isEqualTo("activation.ftl");
    }
}
