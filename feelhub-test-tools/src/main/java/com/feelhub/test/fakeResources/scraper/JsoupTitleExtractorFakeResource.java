package com.feelhub.test.fakeResources.scraper;

import org.restlet.data.MediaType;
import org.restlet.representation.*;
import org.restlet.resource.*;

public class JsoupTitleExtractorFakeResource extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html><title>Webpage title</title><body></body></html>";
        return new StringRepresentation(html, MediaType.TEXT_HTML);
    }
}
