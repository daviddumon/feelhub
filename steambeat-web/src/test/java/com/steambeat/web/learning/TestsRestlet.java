package com.steambeat.web.learning;

import org.junit.Test;
import org.restlet.Application;
import org.restlet.routing.*;
import org.restlet.util.RouteList;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsRestlet {

    @Test
    public void canGetNextOfFilter() {
        final Filter finder1 = new FakeFilter();
        final Filter finder2 = new FakeFilter();

        finder1.setNext(finder2);

        assertThat((Filter) finder1.getNext(), is(finder2));
    }

    @Test
    public void canAttachAFinderToApplication() {
        final Application application = new Application();
        final Filter finder1 = new FakeFilter();
        final Filter finder2 = new FakeFilter();

        finder1.setNext(finder2);
        application.setInboundRoot(finder1);

        assertThat((Filter) application.getInboundRoot(), is(finder1));
        assertThat((Filter) ((Filter) application.getInboundRoot()).getNext(), is(finder2));
    }

    @Test
    public void canStopAFinderViaApplication() {
        final Application application = new Application();
        final Filter finder1 = new FakeFilter();
        final Filter finder2 = new FakeFilter();
        finder1.setNext(finder2);
        application.setInboundRoot(finder1);

        try {
            ((Filter) application.getInboundRoot()).getNext().stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThat(((Filter) application.getInboundRoot()).getNext().isStopped(), is(true));
    }

    @Test
    public void canAttachARouterAttachToAnApplication() {
        final Application application = new Application();
        final Router router = new Router();
        final Router migrationRouter = new Router();

        router.attach(migrationRouter);
        application.setInboundRoot(router);

        assertThat((Router) application.getInboundRoot(), is(router));
    }

    @Test
    public void canStopARouter() throws Exception {
        final Router router = new Router();

        router.stop();

        assertThat(router.isStopped(), is(true));
    }

    @Test
    public void canStopARoute() throws Exception {
        final Router router = new Router();
        final Router migrationRouter = new Router();
        final Application application = new Application();
        router.attach(migrationRouter);
        application.setInboundRoot(router);

        final RouteList routes = ((Router) application.getInboundRoot()).getRoutes();

        assertThat(routes.size(), is(1));

        ((Router) application.getInboundRoot()).detach(migrationRouter);

        assertThat(((Router) application.getInboundRoot()).getRoutes().size(), is(0));
    }

    private class FakeFilter extends Filter {

    }


}
