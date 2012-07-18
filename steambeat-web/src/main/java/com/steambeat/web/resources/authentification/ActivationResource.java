package com.steambeat.web.resources.authentification;

import com.google.inject.Inject;
import com.steambeat.application.UserService;
import com.steambeat.domain.user.User;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.UUID;

public class ActivationResource extends ServerResource {

    @Inject
    public ActivationResource(final UserService userService) {
        this.userService = userService;
    }

    @Get
    public Representation createActivation() {
        final UUID secret = UUID.fromString(getRequestAttributes().get("secret").toString());
        final User user = userService.getUserForSecret(secret);
        user.activate();
        setStatus(Status.SUCCESS_OK);
        return SteambeatTemplateRepresentation.createNew("thankyou.ftl", getContext());
    }

    private UserService userService;
}
