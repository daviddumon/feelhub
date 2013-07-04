package com.feelhub.web.resources.authentification;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.tools.*;
import org.junit.*;
import org.restlet.data.Status;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class ActivationResourceTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void redirect() {
        cookieManager = mock(CookieManager.class);
        when(cookieManager.cookieBuilder()).thenReturn(new CookieBuilder(new FeelhubWebProperties()));
        final ActivationResource resource = new ActivationResource(cookieManager);
        ContextTestFactory.initResource(resource);
        resource.getRequest().getAttributes().put("secret", UUID.randomUUID());

        resource.confirm();

        assertThat(resource.getStatus()).isEqualTo(Status.REDIRECTION_TEMPORARY);
    }

    private CookieManager cookieManager;
}
