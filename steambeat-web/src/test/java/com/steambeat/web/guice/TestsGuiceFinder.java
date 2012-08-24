package com.steambeat.web.guice;

import com.google.inject.*;
import com.steambeat.application.*;
import com.steambeat.domain.alchemy.*;
import com.steambeat.domain.keyword.KeywordFactory;
import com.steambeat.domain.reference.ReferenceFactory;
import com.steambeat.domain.scraper.Scraper;
import com.steambeat.repositories.SessionProvider;
import com.steambeat.repositories.fakeRepositories.FakeSessionProvider;
import com.steambeat.test.FakeScraper;
import com.steambeat.web.resources.HomeResource;
import org.junit.Test;
import org.restlet.Context;
import org.restlet.resource.ServerResource;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsGuiceFinder {

    @Test
    public void canRetrieveResource() {
        final Injector injector = Guice.createInjector(new TestModule());
        final GuiceFinder finder = new GuiceFinder(new Context(), HomeResource.class, injector);

        final ServerResource serverResource = finder.create(null, null);

        assertThat(serverResource, instanceOf(HomeResource.class));
    }

    private class TestModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(SessionProvider.class).to(FakeSessionProvider.class);
            bind(Scraper.class).to(FakeScraper.class);
            bind(NamedEntityProvider.class).toInstance(new NamedEntityJsonProvider(new FakeJsonAlchemyLink(), new NamedEntityBuilder(new KeywordService(new KeywordFactory(), new ReferenceService(new ReferenceFactory())))));
        }
    }
}
