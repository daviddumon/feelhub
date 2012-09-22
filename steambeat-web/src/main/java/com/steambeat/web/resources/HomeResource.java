package com.steambeat.web.resources;

import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

public class HomeResource extends ServerResource {

    @Get
    public Representation represent() {
        return SteambeatTemplateRepresentation.createNew("main.ftl", getContext(), getRequest());
    }
}