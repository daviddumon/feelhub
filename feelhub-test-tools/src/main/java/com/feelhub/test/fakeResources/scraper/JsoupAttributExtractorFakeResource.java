package com.feelhub.test.fakeResources.scraper;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class JsoupAttributExtractorFakeResource extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html lang='en'><head>" +
                "<meta name='description' content='description'/>" +
                "</head><body></body></html>";
        return new StringRepresentation(html);
    }
}
