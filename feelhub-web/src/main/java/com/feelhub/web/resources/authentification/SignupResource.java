package com.feelhub.web.resources.authentification;

import com.feelhub.application.UserService;
import com.feelhub.domain.session.EmailAlreadyUsed;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.BadEmail;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.social.FacebookConnector;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

public class SignupResource extends ServerResource {

    @Inject
    public SignupResource(final UserService userService, final FacebookConnector connector) {
        this.userService = userService;
        this.connector = connector;
    }

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("signup.ftl").with("facebookUrl", connector.getUrl()).with("locales", FeelhubLanguage.availables());
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
    private final FacebookConnector connector;
}
