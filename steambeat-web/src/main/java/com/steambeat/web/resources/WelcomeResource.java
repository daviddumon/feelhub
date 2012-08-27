package com.steambeat.web.resources;

import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

public class WelcomeResource extends ServerResource {

    @Get
    public Representation get() {
        return SteambeatTemplateRepresentation.createNew("welcome.ftl", getContext(), getRequest());
    }
}
