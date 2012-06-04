package com.steambeat.test.fakeResources.alchemy;

import com.steambeat.test.TestTemplateRepresentation;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

public class FakeAlchemyResource extends ServerResource {

    @Get
    public Representation get() {
        return new TestTemplateRepresentation("alchemy.ftl", getContext(), MediaType.APPLICATION_XML);
    }
}
