package com.steambeat.test.fakeResources;

import org.restlet.data.Method;
import org.restlet.resource.*;

public class FakeRedirectionResource extends ServerResource {

    @Get
    public void represent() {
        getRequest().setMethod(Method.GET);
        redirectPermanent("http://localhost:6162/http://www.liberation.fr");
    }
}
