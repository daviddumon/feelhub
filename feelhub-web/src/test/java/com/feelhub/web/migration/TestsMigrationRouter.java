package com.feelhub.web.migration;

import com.feelhub.web.guice.*;
import com.feelhub.web.migration.web.MigrationRouter;
import com.google.inject.*;
import org.junit.Test;
import org.restlet.*;
import org.restlet.data.Method;
import org.restlet.routing.TemplateRoute;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsMigrationRouter {

    @Test
    public void useGuiceToCreateResource() {
        final Injector injector = Guice.createInjector(new GuiceTestModule());
        final MigrationRouter router = new MigrationRouter(new Context(), injector);
        final Request request = new Request(Method.GET, "/");

        final Restlet next = ((TemplateRoute) router.getNext(request, new Response(request))).getNext();

        assertThat(next, instanceOf(GuiceFinder.class));
    }
}
