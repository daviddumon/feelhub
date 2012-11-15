package com.feelhub.web.resources.social;

import com.feelhub.web.representation.ModelAndView;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class SocialWelcomeResource extends ServerResource {

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("social/welcome.ftl");
    }
}
