package com.steambeat.web.resources.authentification;

import com.google.inject.Inject;
import com.steambeat.application.UserService;
import com.steambeat.domain.user.User;
import com.steambeat.web.representation.ModelAndView;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.UUID;

public class ActivationResource extends ServerResource {

    @Inject
    public ActivationResource(final UserService userService) {
        this.userService = userService;
    }

    @Get
    public ModelAndView createActivation() {
        final UUID secret = UUID.fromString(getRequestAttributes().get("secret").toString());
        final User user = userService.getUserForSecret(secret);
        if (!user.isActive()) {
            user.activate();
            setStatus(Status.SUCCESS_OK);
            return ModelAndView.createNew("activation.ftl");
        } else {
            throw new AccountAlreadyActivatedException();
        }
    }

    private final UserService userService;
}
