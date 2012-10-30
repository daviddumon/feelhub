package com.feelhub.test.fakeResources.scraper.tools;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class CSSMinerSimple extends ServerResource {

    @Get
    public Representation represent() {
        final String css = "logo { background-url: ('logoUrl');}";
        return new StringRepresentation(css);
    }
}
