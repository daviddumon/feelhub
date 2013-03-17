package com.feelhub.web.resources.admin;

import com.feelhub.web.representation.ModelAndView;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class AdminResource extends ServerResource {
    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("admin/index.ftl");
    }
}
