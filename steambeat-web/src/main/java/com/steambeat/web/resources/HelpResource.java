package com.steambeat.web.resources;

import com.steambeat.web.representation.ModelAndView;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class HelpResource extends ServerResource {

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("help.ftl");
    }
}
