package com.feelhub.web.update;

import com.feelhub.web.representation.ModelAndView;
import org.restlet.resource.*;

public class UpdateResource extends ServerResource {

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("launch.ftl");
    }
}
