package com.feelhub.web.resources.authentification;

import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.social.FacebookConnector;
import com.google.inject.Inject;
import org.restlet.resource.*;

public class LoginResource extends ServerResource {

    @Inject
    public LoginResource(final FacebookConnector facebookConnector) {
        this.facebookConnector = facebookConnector;
    }

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("login.ftl").with("facebookUrl", facebookConnector.getUrl()).with("referrer", getReferrer());
    }

    private String getReferrer() {
        if (getRequest().getReferrerRef() != null) {
            return getRequest().getReferrerRef().toString();
        } else {
            return "";
        }
    }

    private final FacebookConnector facebookConnector;
}
