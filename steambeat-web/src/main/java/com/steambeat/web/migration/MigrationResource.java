package com.steambeat.web.migration;

import com.google.inject.*;
import com.steambeat.web.*;
import com.steambeat.web.guice.SteambeatModule;
import org.restlet.Restlet;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.restlet.routing.*;

public class MigrationResource extends ServerResource {

    @Get
    public Representation represent() {
        //getApplication().setInboundRoot(createInboundRoot());
        return SteambeatTemplateRepresentation.createNew("migration.ftl", getContext());
    }

    private Restlet createInboundRoot() {
        final Router router = new Router(getContext());
        router.attach("/static", new Directory(getContext(), "war:///static"));
        final Filter openSession = injector.getInstance(OpenSessionInViewFilter.class);
        openSession.setContext(getContext());
        final SteambeatRouter steambeatRouter = new SteambeatRouter(getContext(), injector);
        openSession.setNext(steambeatRouter);
        router.attach(openSession);
        return router;
    }

    private Injector injector = Guice.createInjector(new SteambeatModule());
}
