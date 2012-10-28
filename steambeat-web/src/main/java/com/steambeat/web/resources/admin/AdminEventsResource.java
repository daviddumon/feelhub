package com.steambeat.web.resources.admin;

import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.web.representation.ModelAndView;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class AdminEventsResource extends ServerResource {

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("admin/events.ftl").with("events", DomainEventBus.INSTANCE.getEvents());
    }
}
