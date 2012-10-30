package com.feelhub.web.resources.authentification;

import com.feelhub.application.UserService;
import com.feelhub.domain.user.User;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.Inject;
import org.restlet.data.Status;
import org.restlet.resource.*;

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
