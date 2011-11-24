package com.steambeat.web.guice;

import com.google.inject.*;
import com.steambeat.repositories.SessionProvider;
import com.steambeat.test.fakeRepositories.FakeSessionProvider;
import com.steambeat.web.resources.WebPageResource;
import org.junit.Test;
import org.restlet.Context;
import org.restlet.resource.ServerResource;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TestsGuiceFinder {

    @Test
    public void canRetrieveResource() {
        final Injector injector = Guice.createInjector(new TestModule());
        final GuiceFinder finder = new GuiceFinder(new Context(), WebPageResource.class, injector);

        final ServerResource serverResource = finder.create(null, null);

        assertThat(serverResource, instanceOf(WebPageResource.class));
    }

    private class TestModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(SessionProvider.class).to(FakeSessionProvider.class);
        }
    }
}
