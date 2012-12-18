package com.feelhub.web.resources;

import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.representation.ModelAndView;
import org.apache.http.auth.AuthenticationException;
import org.restlet.data.*;
import org.restlet.resource.*;

public class AnalyzeTopicResource extends ServerResource {

    @Get
    public ModelAndView analyze() {
        try {
            checkCredentials();
            return ModelAndView.createNew("analyze.ftl", MediaType.TEXT_HTML);
        } catch (AuthenticationException e) {
            setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/login"));
            return ModelAndView.createNew("analyze.ftl", MediaType.TEXT_HTML);
        }
    }

    private void checkCredentials() throws AuthenticationException {
        if (!CurrentUser.get().isAuthenticated()) {
            throw new AuthenticationException();
        }
    }
}
