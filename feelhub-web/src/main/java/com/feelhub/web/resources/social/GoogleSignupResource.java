package com.feelhub.web.resources.social;

import com.feelhub.web.social.GoogleConnector;
import com.google.inject.Inject;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class GoogleSignupResource extends ServerResource {

    @Inject
    public GoogleSignupResource(GoogleConnector connector) {
        this.connector = connector;
    }

    @Get
    public void redirect() {
        setStatus(Status.REDIRECTION_TEMPORARY);
        setLocationRef(connector.getUrl());
    }

    private GoogleConnector connector;
}
