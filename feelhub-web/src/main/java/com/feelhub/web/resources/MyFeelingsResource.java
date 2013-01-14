package com.feelhub.web.resources;

import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.representation.ModelAndView;
import org.apache.http.auth.AuthenticationException;
import org.restlet.resource.*;

public class MyFeelingsResource extends ServerResource {

    @Get
    public ModelAndView getMyFeelings() {
        try {
            checkCredentials();
            return ModelAndView.createNew("myfeelings.ftl");
        } catch (AuthenticationException e) {
            redirectSeeOther(new WebReferenceBuilder(getContext()).buildUri("/login"));
            return ModelAndView.createNew("empty.ftl");
        }
    }

    private void checkCredentials() throws AuthenticationException {
        if (!CurrentUser.get().isAuthenticated()) {
            throw new AuthenticationException();
        }
    }
}
