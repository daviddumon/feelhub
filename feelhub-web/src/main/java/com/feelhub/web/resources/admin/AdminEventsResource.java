package com.feelhub.web.resources.admin;

import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class AdminEventsResource extends ServerResource {

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("admin/events.ftl").with("events", Lists.newArrayList());
    }
}
