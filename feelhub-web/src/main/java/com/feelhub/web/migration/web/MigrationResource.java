package com.feelhub.web.migration.web;

import com.feelhub.web.representation.ModelAndView;
import org.restlet.resource.*;

public class MigrationResource extends ServerResource {

    @Get
    public ModelAndView represent() throws Exception {
        return ModelAndView.createNew("migration.ftl");
    }
}
