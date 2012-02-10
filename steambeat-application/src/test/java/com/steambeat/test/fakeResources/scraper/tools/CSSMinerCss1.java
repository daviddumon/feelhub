package com.steambeat.test.fakeResources.scraper.tools;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class CSSMinerCss1 extends ServerResource {

    @Get
    public Representation represent() {
        final String css = "lol { background-url: ('bad.png');}";
        return new StringRepresentation(css);
    }
}
