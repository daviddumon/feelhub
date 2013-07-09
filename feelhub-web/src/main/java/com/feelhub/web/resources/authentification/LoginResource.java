package com.feelhub.web.resources.authentification;

import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.social.FacebookConnector;
import com.google.inject.Inject;
import org.restlet.data.Reference;
import org.restlet.resource.*;

public class LoginResource extends ServerResource {

    @Inject
    public LoginResource(final FacebookConnector facebookConnector) {
        this.facebookConnector = facebookConnector;
    }

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("login.ftl")
                .with("googleUrl", new WebReferenceBuilder(getContext()).buildUri("/social/google-signup"))
                .with("facebookUrl", facebookConnector.getUrl())
                .with("referrer", getReferrer());
    }

    private String getReferrer() {
        final Reference referrerRef = getRequest().getReferrerRef();
        if (referrerRef != null) {
            if (!referrerRef.toString().equalsIgnoreCase(getLogin()) && !referrerRef.toString().isEmpty()) {
                return referrerRef.toString();
            }
        }
        return new WebReferenceBuilder(getContext()).buildUri("/");
    }

    public String getLogin() {
        return new WebReferenceBuilder(getContext()).buildUri("/login");
    }

    private final FacebookConnector facebookConnector;
}
