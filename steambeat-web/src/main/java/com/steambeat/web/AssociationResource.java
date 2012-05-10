package com.steambeat.web;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class AssociationResource extends ServerResource {

    @Get
    public Representation represent() {
        return new StringRepresentation("e");
    }
}
