package com.feelhub.test.fakeResources.scraper;

import org.restlet.data.MediaType;
import org.restlet.representation.*;
import org.restlet.resource.*;

public class EmptyFakeResource extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html></html>";
        return new StringRepresentation(html, MediaType.TEXT_HTML);
    }
}
