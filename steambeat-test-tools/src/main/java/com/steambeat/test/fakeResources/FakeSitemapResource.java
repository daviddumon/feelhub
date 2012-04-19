package com.steambeat.test.fakeResources;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class FakeSitemapResource extends ServerResource {

    @Get
    public Representation represent() {
        final String index = getRequestAttributes().get("index").toString();
        return new StringRepresentation("index : " + index);
    }
}
