package com.steambeat.web.resources.admin;

import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

public class AdminEventsResource extends ServerResource {

    @Get
    public Representation get() {
        return SteambeatTemplateRepresentation.createNew("admin/events.ftl", getContext(), getRequest()).with("events", DomainEventBus.INSTANCE.getEvents());
    }
}
