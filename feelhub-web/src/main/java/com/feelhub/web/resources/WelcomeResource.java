package com.feelhub.web.resources;

import com.feelhub.web.representation.ModelAndView;
import org.restlet.resource.*;

public class WelcomeResource extends ServerResource {

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("welcome.ftl");
    }
}
