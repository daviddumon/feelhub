package com.steambeat.web.guice;

import com.google.inject.Injector;
import org.restlet.*;
import org.restlet.resource.*;

public class GuiceFinder extends Finder {

    public GuiceFinder(final Context context, final Class<?> target, final Injector injector) {
        super(context, target);
        this.injector = injector;
    }

    @Override
    public ServerResource create(final Request request, final Response response) {
        return (ServerResource) injector.getInstance(getTargetClass());
    }

    private Injector injector;
}
