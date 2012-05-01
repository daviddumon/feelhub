package com.steambeat.web;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class RelationsResource extends ServerResource {

    @Get
    public Representation represent() {
        return new StringRepresentation("a");
    }
}
