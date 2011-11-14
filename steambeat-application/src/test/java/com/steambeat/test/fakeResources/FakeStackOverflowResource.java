package com.steambeat.test.fakeResources;

import org.restlet.data.Status;
import org.restlet.resource.*;

public class FakeStackOverflowResource extends ServerResource {

    @Get
    public void represent() {
        setStatus(Status.SERVER_ERROR_INTERNAL);
    }
}
