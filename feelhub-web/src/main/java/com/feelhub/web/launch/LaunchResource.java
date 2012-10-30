package com.feelhub.web.launch;

import com.feelhub.web.representation.ModelAndView;
import org.restlet.resource.*;

public class LaunchResource extends ServerResource {

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("launch.ftl");
    }
}
