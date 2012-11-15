package com.feelhub.test.fakeResources.bing;

import org.restlet.data.Status;
import org.restlet.representation.*;
import org.restlet.resource.*;

public class BingImageFakeResource extends ServerResource {

    @Get
    public Representation represent() {
        setStatus(Status.SUCCESS_OK);
        return new StringRepresentation("image");
    }
}
