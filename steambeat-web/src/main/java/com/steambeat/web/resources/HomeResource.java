package com.steambeat.web.resources;

import com.steambeat.repositories.Repositories;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

public class HomeResource extends ServerResource {

    @Get
    public Representation represent() {
        return SteambeatTemplateRepresentation.createNew("home.ftl", getContext())
                .with("steam", Repositories.subjects().getSteam())
                .with("identity", getContext().getAttributes().get("com.steambeat.identity"));
    }
}




