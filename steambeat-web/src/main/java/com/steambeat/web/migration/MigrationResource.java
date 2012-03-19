package com.steambeat.web.migration;

import com.steambeat.web.SteambeatTemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.restlet.routing.*;
import org.restlet.util.RouteList;

public class MigrationResource extends ServerResource {

    @Get
    public Representation represent() throws Exception {
        final Router mainRouter = (Router) getApplication().getInboundRoot();
        final RouteList routes = mainRouter.getRoutes();
        final Route route = routes.get(1);
        route.stop();
        return SteambeatTemplateRepresentation.createNew("migration.ftl", getContext());
    }
}
