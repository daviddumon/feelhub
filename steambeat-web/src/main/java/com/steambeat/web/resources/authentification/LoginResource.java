package com.steambeat.web.resources.authentification;

import com.google.inject.Inject;
import com.steambeat.web.representation.ModelAndView;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

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
