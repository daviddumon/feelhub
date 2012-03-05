package com.steambeat.web.resources;

import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

public class SubjectResource extends ServerResource {

    @Get
    public Representation represent() {
        return new JsonRepresentation(new JSONObject());
    }
}
