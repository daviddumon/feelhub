package com.steambeat.web.migration.web;

import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

public class MigrationResource extends ServerResource {

    @Get
    public Representation represent() throws Exception {
        return SteambeatTemplateRepresentation.createNew("migration.ftl", getContext());
    }
}
