package com.feelhub.web.resources.admin;

import com.feelhub.web.representation.ModelAndView;
import org.restlet.resource.*;

public class AdminFreemarkerResource extends ServerResource {

    @Get
    public ModelAndView represent() {
        final String name = getRequestAttributes().get("name").toString();
        return ModelAndView.createNew(name + ".ftl");
    }
}
