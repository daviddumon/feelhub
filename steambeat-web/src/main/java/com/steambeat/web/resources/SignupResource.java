package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.application.*;
import org.restlet.data.*;
import org.restlet.resource.*;

public class SignupResource extends ServerResource {

    @Inject
    public SignupResource(final UserService userService) {
        this.userService = userService;
    }

    @Post
    public void post(final Form form) {
        final String email = form.getFirstValue("email");
        final String password = form.getFirstValue("password");
        try {
            userService.createUser(email, password);
            setStatus(Status.SUCCESS_CREATED);
        } catch (EmailAlreadyUsed emailAlreadyUsed) {
            setStatus(Status.CLIENT_ERROR_CONFLICT);
        } finally {
            setLocationRef(getReferrerRef());
        }
    }

    private UserService userService;
}
