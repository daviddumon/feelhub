package com.feelhub.web.migration.web;

import com.feelhub.web.guice.GuiceFinder;
import com.google.inject.Injector;
import org.restlet.Context;
import org.restlet.resource.Finder;
import org.restlet.routing.Router;

public class MigrationRouter extends Router {

    public MigrationRouter(final Context context, final Injector injector) {
        super(context);
        this.injector = injector;
        setDefaultMatchingMode(MODE_BEST_MATCH);
        attachResources();
    }

    private void attachResources() {
        attach("/", MigrationResource.class);
    }

    @Override
    public Finder createFinder(final Class<?> targetClass) {
        return new GuiceFinder(getContext(), targetClass, injector);
    }

    private final Injector injector;
}