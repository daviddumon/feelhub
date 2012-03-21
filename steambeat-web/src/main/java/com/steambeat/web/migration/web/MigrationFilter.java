package com.steambeat.web.migration.web;

import com.google.inject.*;
import com.steambeat.web.SteambeatRouter;
import com.steambeat.web.guice.SteambeatModule;
import org.restlet.*;
import org.restlet.routing.Filter;

public class MigrationFilter extends Filter {

    @Override
    protected int beforeHandle(final Request request, final Response response) {
        final Boolean ready = (Boolean) getContext().getAttributes().get("com.steambeat.ready");
        if (ready) {
            setNext(new SteambeatRouter(getContext(), injector));
        } else {
            setNext(new MigrationRouter(getContext(), injector));
        }
        return CONTINUE;
    }

    private Injector injector = Guice.createInjector(new SteambeatModule());
}
