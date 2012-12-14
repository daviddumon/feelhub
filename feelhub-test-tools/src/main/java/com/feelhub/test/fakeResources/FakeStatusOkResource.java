package com.feelhub.test.fakeResources;

import org.restlet.data.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

public class FakeStatusOkResource extends ServerResource {

    @Get
    public Representation represent() {
        setStatus(Status.SUCCESS_OK);
        final StringRepresentation stringRepresentation = new StringRepresentation("");
        stringRepresentation.setMediaType(MediaType.TEXT_HTML);
        return stringRepresentation;
    }
}
