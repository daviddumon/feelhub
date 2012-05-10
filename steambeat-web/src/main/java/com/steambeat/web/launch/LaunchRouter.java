package com.steambeat.web.launch;

import com.google.inject.Injector;
import com.steambeat.web.guice.GuiceFinder;
import org.restlet.Context;
import org.restlet.resource.Finder;
import org.restlet.routing.Router;

public class LaunchRouter extends Router {

    public LaunchRouter(final Context context, final Injector injector) {
        super(context);
        this.injector = injector;
        setDefaultMatchingMode(MODE_FIRST_MATCH);
        attachDefault(LaunchResource.class);
    }

    @Override
    public Finder createFinder(final Class<?> targetClass) {
        return new GuiceFinder(getContext(), targetClass, injector);
    }

    private final Injector injector;
}
