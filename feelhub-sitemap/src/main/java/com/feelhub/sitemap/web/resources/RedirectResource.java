package com.feelhub.sitemap.web.resources;

import org.restlet.data.Status;
import org.restlet.representation.*;
import org.restlet.resource.*;

public class RedirectResource extends ServerResource {

    @Get
    public Representation represent() {
        setStatus(Status.REDIRECTION_PERMANENT);
        setLocationRef("https://www.feelhub.com");
        return new EmptyRepresentation();
    }
}
