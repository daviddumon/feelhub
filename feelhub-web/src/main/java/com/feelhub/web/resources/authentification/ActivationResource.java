package com.feelhub.web.resources.authentification;

import com.feelhub.application.ActivationService;
import com.feelhub.domain.user.ActivationNotFoundException;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.Inject;
import org.restlet.data.Status;
import org.restlet.resource.*;

import java.util.UUID;

public class ActivationResource extends ServerResource {

    @Inject
    public ActivationResource(final ActivationService activationService) {
        this.activationService = activationService;
    }

    @Get
    public ModelAndView confirm() {
        final UUID secret = UUID.fromString(getRequestAttributes().get("secret").toString());
        try {
            activationService.confirm(secret);
        } catch (ActivationNotFoundException e) {
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/"));
            setStatus(Status.REDIRECTION_PERMANENT);
        }
        return ModelAndView.createNew("activation.ftl");
    }

    private final ActivationService activationService;
}
