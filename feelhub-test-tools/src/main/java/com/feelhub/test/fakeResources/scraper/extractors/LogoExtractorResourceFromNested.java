package com.feelhub.test.fakeResources.scraper.extractors;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class LogoExtractorResourceFromNested extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html><body><div id='logo'><img src='http://www.google.fr/images/lol.jpg'/></div></body></html>";
        return new StringRepresentation(html);
    }
}
