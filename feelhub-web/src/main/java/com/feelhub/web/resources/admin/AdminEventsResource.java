package com.feelhub.web.resources.admin;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.web.representation.ModelAndView;
import org.restlet.resource.*;

public class AdminEventsResource extends ServerResource {

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("admin/events.ftl").with("events", DomainEventBus.INSTANCE.getEvents());
    }
}
