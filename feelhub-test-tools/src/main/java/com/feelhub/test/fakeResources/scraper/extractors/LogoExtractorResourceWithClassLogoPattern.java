package com.feelhub.test.fakeResources.scraper.extractors;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class LogoExtractorResourceWithClassLogoPattern extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html><body><img src='http://www.google.fr/images/lol.jpg' /><img class='myLogo_v1' src='http://www.image.com/image.jpg'/></body></html>";
        return new StringRepresentation(html);
    }
}
