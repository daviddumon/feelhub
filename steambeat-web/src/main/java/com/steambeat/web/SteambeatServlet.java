package com.steambeat.web;

import com.steambeat.web.guice.GuiceProductionModule;
import org.restlet.*;
import org.restlet.ext.servlet.ServerServlet;

public class SteambeatServlet extends ServerServlet {

    @Override
    protected Application createApplication(final Context parentContext) {
        final SteambeatApplication application = (SteambeatApplication) super.createApplication(parentContext);
        application.initializeGuice(new GuiceProductionModule());
        return application;
    }
}
