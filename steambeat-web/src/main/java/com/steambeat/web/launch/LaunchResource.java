package com.steambeat.web.launch;

import com.steambeat.web.representation.ModelAndView;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class LaunchResource extends ServerResource {

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("launch.ftl");
    }
}
