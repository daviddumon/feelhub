package com.steambeat.web;

import com.google.inject.*;
import com.steambeat.web.guice.GuiceFinder;
import com.steambeat.web.test.guice.SteambeatModuleForTest;
import org.junit.Test;
import org.restlet.*;
import org.restlet.data.Method;
import org.restlet.routing.TemplateRoute;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class TestsSteambeatRouter {

    @Test
    public void useGuiceToCreateResource() {
        final Injector injector = Guice.createInjector(new SteambeatModuleForTest());
        final SteambeatRouter router = new SteambeatRouter(new Context(), injector);
        final Request request = new Request(Method.GET, "/");

        final Restlet next = ((TemplateRoute) router.getNext(request, new Response(request))).getNext();

        assertThat(next, instanceOf(GuiceFinder.class));
    }
}
