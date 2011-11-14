package com.steambeat.test.fakeResources;

import org.restlet.data.Status;
import org.restlet.resource.*;

public class FakeStatusOkResource extends ServerResource {

    @Get
    public void represent() {
        setStatus(Status.SUCCESS_OK);
    }
}
