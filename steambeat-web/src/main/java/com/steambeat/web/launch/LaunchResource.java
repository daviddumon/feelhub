package com.steambeat.web.launch;

import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

public class LaunchResource extends ServerResource {

    @Get
    public Representation represent() {
        return SteambeatTemplateRepresentation.createNew("launch/launch.ftl", getContext(), getRequest());
    }
}
