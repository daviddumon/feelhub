package com.steambeat.web.resources.testResources;

import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

public class FreemarkerResource extends ServerResource{

    @Get
    public Representation get() {
        final String name = getRequestAttributes().get("name").toString();
        return SteambeatTemplateRepresentation.createNew(name + ".ftl", getContext());
    }
}
