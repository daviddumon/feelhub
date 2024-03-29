package com.feelhub.web.guice;

import com.google.inject.Injector;
import org.restlet.*;
import org.restlet.resource.*;

public class GuiceFinder extends Finder {

    public GuiceFinder(final Context context, final Class<? extends ServerResource> target, final Injector injector) {
        super(context, target);
        this.injector = injector;
    }

    @Override
    public ServerResource create(final Request request, final Response response) {
        final ServerResource result = (ServerResource) injector.getInstance(getTargetClass());
        return result;
    }

    private final Injector injector;
}
