package com.steambeat.web.resources.authentification;

import com.google.inject.Inject;
import com.steambeat.application.UserService;
import com.steambeat.domain.session.EmailAlreadyUsed;
import com.steambeat.domain.user.BadEmail;
import com.steambeat.web.representation.ModelAndView;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class SignupResource extends ServerResource {

    @Inject
    public SignupResource(final UserService userService) {
        this.userService = userService;
    }

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("signup.ftl");
    }

    @Post
    public void signup(final Form form) {
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

    private final UserService userService;
}
