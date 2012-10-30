package com.feelhub.web.resources;

import com.feelhub.web.representation.ModelAndView;
import org.restlet.resource.*;

public class HomeResource extends ServerResource {

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("main.ftl");
    }
}