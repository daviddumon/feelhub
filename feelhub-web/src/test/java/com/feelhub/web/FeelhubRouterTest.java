package com.feelhub.web;

import com.feelhub.web.guice.*;
import com.google.inject.*;
import org.junit.Test;
import org.restlet.*;
import org.restlet.data.Method;
import org.restlet.routing.TemplateRoute;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class FeelhubRouterTest {

    @Test
    public void useGuiceToCreateResource() {
        final Injector injector = Guice.createInjector(new GuiceTestModule());
        final FeelhubRouter router = new FeelhubRouter(new Context(), injector);
        final Request request = new Request(Method.GET, "/");

        final Restlet next = ((TemplateRoute) router.getNext(request, new Response(request))).getNext();

        assertThat(next, instanceOf(GuiceFinder.class));
    }
}
