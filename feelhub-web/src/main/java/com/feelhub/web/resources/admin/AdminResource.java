package com.feelhub.web.resources.admin;

import com.feelhub.web.representation.ModelAndView;
import org.restlet.resource.*;

public class AdminResource extends ServerResource {
    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("admin/index.ftl");
    }
}
