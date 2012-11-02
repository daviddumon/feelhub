package com.feelhub.web.resources.authentification;

import com.feelhub.web.authentification.FacebookConnector;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.Inject;
import org.restlet.resource.*;

public class LoginResource extends ServerResource {

    @Inject
    public LoginResource(final FacebookConnector facebookConnector) {
        this.facebookConnector = facebookConnector;
    }

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("login.ftl").with("facebookUrl", facebookConnector.getUrl());
    }

    private FacebookConnector facebookConnector;
}
