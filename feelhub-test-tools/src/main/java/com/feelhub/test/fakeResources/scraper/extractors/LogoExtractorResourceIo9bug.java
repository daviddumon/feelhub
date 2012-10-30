package com.feelhub.test.fakeResources.scraper.extractors;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class LogoExtractorResourceIo9bug extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<img id='logo' src='' style='display: none;'/>";
        return new StringRepresentation(html);
    }
}
