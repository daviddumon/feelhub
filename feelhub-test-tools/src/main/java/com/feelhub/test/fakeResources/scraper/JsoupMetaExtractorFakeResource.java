package com.feelhub.test.fakeResources.scraper;

import org.restlet.data.MediaType;
import org.restlet.representation.*;
import org.restlet.resource.*;

public class JsoupMetaExtractorFakeResource extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html><head>" +
                "<meta name='description' content='description'/>" +
                "</head><body></body></html>";
        return new StringRepresentation(html, MediaType.TEXT_HTML);
    }
}
