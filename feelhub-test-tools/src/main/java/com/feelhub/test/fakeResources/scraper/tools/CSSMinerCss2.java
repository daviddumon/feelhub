package com.feelhub.test.fakeResources.scraper.tools;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class CSSMinerCss2 extends ServerResource {

    @Get
    public Representation represent() {
        final String css = "gniark { background-url: ('bad.png');}" +
                "#log { background-url: ('bad.png');}" +
                "#logoimage { background-url: ('good.png');}";
        return new StringRepresentation(css);
    }
}
