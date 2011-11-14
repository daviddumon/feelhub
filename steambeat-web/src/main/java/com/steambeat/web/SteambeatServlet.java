package com.steambeat.web;

import org.restlet.*;
import org.restlet.ext.servlet.ServerServlet;

public class SteambeatServlet extends ServerServlet {

    @Override
    protected Application createApplication(final Context parentContext) {
        System.out.println("application :: start");
        return super.createApplication(parentContext);
    }
}
