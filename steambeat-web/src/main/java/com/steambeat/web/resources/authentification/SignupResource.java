package com.steambeat.web.resources.authentification;

import com.google.inject.Inject;
import com.steambeat.application.UserService;
import com.steambeat.domain.session.EmailAlreadyUsed;
import com.steambeat.domain.user.BadEmail;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.restlet.data.*;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

public class SignupResource extends ServerResource {

    @Inject
    public SignupResource(final UserService userService) {
        this.userService = userService;
    }

    @Get
    public Representation represent() {
        return SteambeatTemplateRepresentation.createNew("signup.ftl", getContext(), getRequest());
    }

    @Post
    public void post(final Form form) {
        if (checkForm(form)) {
            final String email = form.getFirstValue("email");
            final String password = form.getFirstValue("password");
            final String fullname = form.getFirstValue("fullname");
            final String language = form.getFirstValue("language");
            try {
                userService.createUser(email, password, fullname, language);
                setStatus(Status.SUCCESS_CREATED);
            } catch (EmailAlreadyUsed emailAlreadyUsed) {
                setStatus(Status.CLIENT_ERROR_CONFLICT);
            } catch (BadEmail badEmail) {
                setStatus(Status.CLIENT_ERROR_PRECONDITION_FAILED);
            }
        } else {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

    private boolean checkForm(final Form form) {
        return form.getQueryString().contains("email")
                && form.getQueryString().contains("password")
                && form.getQueryString().contains("fullname")
                && form.getQueryString().contains("language");
    }

    private UserService userService;
}
