package com.steambeat.web.migration;

import com.google.inject.*;
import com.steambeat.web.guice.GuiceFinder;
import com.steambeat.web.migration.web.MigrationRouter;
import com.steambeat.web.test.guice.TestGuiceModule;
import org.junit.Test;
import org.restlet.*;
import org.restlet.data.Method;
import org.restlet.routing.TemplateRoute;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class TestsMigrationRouter {

    @Test
    public void useGuiceToCreateResource() {
        final Injector injector = Guice.createInjector(new TestGuiceModule());
        final MigrationRouter router = new MigrationRouter(new Context(), injector);
        final Request request = new Request(Method.GET, "/");

        final Restlet next = ((TemplateRoute) router.getNext(request, new Response(request))).getNext();

        assertThat(next, instanceOf(GuiceFinder.class));
    }
}
