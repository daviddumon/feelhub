package com.feelhub.test.fakeResources.scraper;

import org.restlet.data.MediaType;
import org.restlet.representation.*;
import org.restlet.resource.*;

public class JsoupTagExtractorFakeResource extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html><head><title></title></head><body><h1><p>h1 nested text</p></h1><h2>First h2 section</h2><h3>First h3 section</h3></body></html>";
        return new StringRepresentation(html, MediaType.TEXT_HTML);
    }
}
