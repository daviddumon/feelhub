package com.steambeat.test.fakeResources.alchemy;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class FakeAlchemyResource extends ServerResource {

    @Get
    public Representation get() {

        return new StringRepresentation("");
    }
}
