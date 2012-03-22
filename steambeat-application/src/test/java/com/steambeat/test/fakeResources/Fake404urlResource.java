package com.steambeat.test.fakeResources;

import org.restlet.data.Status;
import org.restlet.resource.*;

public class Fake404urlResource extends ServerResource {

    @Get
    public void represent() {
        setStatus(Status.CLIENT_ERROR_NOT_FOUND);
    }
}
