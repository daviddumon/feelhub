package com.feelhub.web;

import com.feelhub.web.guice.GuiceProductionModule;
import org.restlet.*;
import org.restlet.ext.servlet.ServerServlet;

public class FeelhubServlet extends ServerServlet {

    @Override
    protected Application createApplication(final Context parentContext) {
        final FeelhubApplication application = (FeelhubApplication) super.createApplication(parentContext);
        application.initializeGuice(new GuiceProductionModule());
        return application;
    }
}
