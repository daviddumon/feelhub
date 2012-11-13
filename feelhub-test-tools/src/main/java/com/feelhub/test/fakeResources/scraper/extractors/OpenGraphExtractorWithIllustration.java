package com.feelhub.test.fakeResources.scraper.extractors;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class OpenGraphExtractorWithIllustration extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html><head><meta property='og:image' content='http://i4.ytimg.com/vi/_IOqGclele4/mqdefault.jpg'></head><body></body></html>";
        return new StringRepresentation(html);
    }
}
