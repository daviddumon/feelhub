package com.steambeat.test.fakeResources;

import org.restlet.data.Status;
import org.restlet.resource.*;

import java.net.UnknownHostException;

public class FakeBadurlResource extends ServerResource {

    @Get
    public void represent() throws UnknownHostException {
        setStatus(Status.CONNECTOR_ERROR_COMMUNICATION);
    }
}
