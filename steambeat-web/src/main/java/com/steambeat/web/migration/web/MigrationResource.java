package com.steambeat.web.migration.web;

import com.steambeat.web.representation.ModelAndView;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class MigrationResource extends ServerResource {

    @Get
    public ModelAndView represent() throws Exception {
        return ModelAndView.createNew("migration.ftl");
    }
}
