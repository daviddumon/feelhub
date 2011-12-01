package com.steambeat.web.resources;

import com.steambeat.web.SteambeatTemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

public class AboutResource extends ServerResource {

    @Get
    public Representation represent() {
        return SteambeatTemplateRepresentation.createNew("about.ftl", getContext());
    }
}
