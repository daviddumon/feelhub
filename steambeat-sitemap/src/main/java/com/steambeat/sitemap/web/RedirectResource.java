package com.steambeat.sitemap.web;

import org.restlet.data.Status;
import org.restlet.representation.*;
import org.restlet.resource.*;

public class RedirectResource extends ServerResource {

    @Get
    public Representation represent() {
        setStatus(Status.REDIRECTION_PERMANENT);
        setLocationRef("http://www.steambeat.com");
        return new EmptyRepresentation();
    }
}
