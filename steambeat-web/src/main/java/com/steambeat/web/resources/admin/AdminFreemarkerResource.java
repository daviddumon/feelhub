package com.steambeat.web.resources.admin;

import com.steambeat.web.representation.ModelAndView;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class AdminFreemarkerResource extends ServerResource {

    @Get
    public ModelAndView represent() {
        final String name = getRequestAttributes().get("name").toString();
        return ModelAndView.createNew(name + ".ftl");
    }
}
