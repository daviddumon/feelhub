package com.feelhub.web.resources.authentification;

import com.feelhub.domain.user.BadPasswordException;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.*;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

public class SessionsResource extends ServerResource {

    @Inject
    public SessionsResource(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Post
    public void login(final Form form) {
        if (!checkForm(form)) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return;
        }
        try {
            authenticationManager.authenticate(extractUserDetails(form));
            setStatus(Status.SUCCESS_CREATED);
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/"));
        } catch (BadPasswordException badPasswordException) {
            setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
        }
    }

    private AuthRequest extractUserDetails(final Form form) {
        final String email = form.getFirstValue("email").toLowerCase();
        final String password = form.getFirstValue("password");
        final boolean remember = toBoolean(form.getFirstValue("remember"));
        return new AuthRequest(email, password, remember);
    }

    private boolean toBoolean(final String value) {
        return !Strings.isNullOrEmpty(value) && value.equalsIgnoreCase("on");
    }

    private boolean checkForm(final Form form) {
        return form.getQueryString().contains("email") && form.getQueryString().contains("password");
    }

    @Delete
    public void logout() {
        if (authenticationManager.logout()) {
            setStatus(Status.SUCCESS_ACCEPTED);
        } else {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

    private final AuthenticationManager authenticationManager;
}
