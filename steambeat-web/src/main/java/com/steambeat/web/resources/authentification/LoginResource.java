package com.steambeat.web.resources.authentification;

import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

public class LoginResource extends ServerResource {

    @Get
    public Representation get() {
        return SteambeatTemplateRepresentation.createNew("login.ftl", getContext(), getRequest());
    }
}
