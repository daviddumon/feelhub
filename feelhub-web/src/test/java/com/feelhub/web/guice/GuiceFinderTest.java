package com.feelhub.web.guice;

import com.feelhub.domain.alchemy.*;
import com.feelhub.domain.scraper.*;
import com.feelhub.repositories.SessionProvider;
import com.feelhub.repositories.fakeRepositories.FakeSessionProvider;
import com.feelhub.web.update.UpdateResource;
import com.google.inject.*;
import org.junit.Test;
import org.restlet.Context;
import org.restlet.resource.ServerResource;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class GuiceFinderTest {

    @Test
    public void canRetrieveResource() {
        final Injector injector = Guice.createInjector(new TestModule());
        final GuiceFinder finder = new GuiceFinder(new Context(), UpdateResource.class, injector);

        final ServerResource serverResource = finder.create(null, null);

        assertThat(serverResource, instanceOf(UpdateResource.class));
    }

    private class TestModule extends AbstractModule {

        @Override
        protected void configure() {
            bind(SessionProvider.class).to(FakeSessionProvider.class);
            bind(Scraper.class).to(FakeScraper.class);
            bind(NamedEntityProvider.class).toInstance(new NamedEntityProvider(new FakeAlchemyLink()));
        }
    }
}
